package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.util.helper.Ensure;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public abstract class AbstractObserver {
  private final Supplier<Long> clock;

  protected AbstractObserver(final Supplier<Long> clock) {
    Ensure.suppliesNotNull(clock);
    this.clock = clock;
  }

  private void sleepShorterTime(final long first, final long second) {
    Ensure.notNegative(first);
    Ensure.notNegative(second);
    try {
      if (first < second) {
        Thread.sleep(first);
      } else {
        Thread.sleep(second);
      }
    } catch (InterruptedException exception) {
      throw new RuntimeException(exception);
    }
  }

  protected <T> Optional<T> waitUntilOptionalIsPresent(final Supplier<Optional<T>> positionSupplier,
                                                    final long timeout,
                                                    final long refreshInterval) {
    Ensure.notNull(positionSupplier);
    Ensure.greater(timeout, 0);
    Ensure.greater(refreshInterval, 0);

    long start = getClock().get();
    long end = start + timeout;
    long remaining = end - start;

    Optional<T> result = positionSupplier.get();

    while (!result.isPresent() && remaining > 0) {
      sleepShorterTime(remaining, refreshInterval);
      remaining = end - getClock().get();
      result = positionSupplier.get();
    }

    return result;
  }

  protected <T> Optional<T> waitWhileOptionalIsPresent(final Supplier<Optional<T>>supplier, final Predicate<T> check,
                                   final long timeout, final long refreshInterval) {
    Ensure.notNull(supplier);
    Ensure.notNull(check);
    Ensure.notNegative(timeout);
    Ensure.greater(refreshInterval, 0);

    long start = getClock().get();
    long end = start + timeout;
    long remaining = end - start;

    Optional<T> result = supplier.get();

    while (result.isPresent() && remaining > 0) {
      sleepShorterTime(remaining, refreshInterval);
      remaining = end - getClock().get();
      if (!check.test(result.get())) {
        result = supplier.get();
        if (result.isPresent()) {
          result = Optional.empty();
        }
      }
    }

    return result;
  }
}
