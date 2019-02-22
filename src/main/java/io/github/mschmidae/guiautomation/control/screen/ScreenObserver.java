package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.helper.Ensure;
import io.github.mschmidae.guiautomation.util.image.Image;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public class ScreenObserver extends AbstractObserver {
  public static final int DEFAULT_REFRESH_INTERVAL = 1_000;

  private final Screen screen;
  private final Supplier<Long> clock;
  private final int refreshInterval;
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public ScreenObserver(final Screen screen) {
    this(screen, System::currentTimeMillis, DEFAULT_REFRESH_INTERVAL);
  }

  public ScreenObserver(final Screen screen, final Supplier<Long> clock,
                        final int refreshInterval) {
    super(clock);
    Ensure.notNull(screen);
    Ensure.notNegative(refreshInterval);
    this.screen = screen;
    this.clock = clock;
    this.refreshInterval = refreshInterval;
  }

  /*
   *
   * waitUntil
   *
   */

  public Optional<Position> waitUntil(final Supplier<Image> patternSupplier, final long timeout) {
    return waitUntil(patternSupplier, timeout, getRefreshInterval());
  }

  public Optional<Position> waitUntil(final Supplier<Image> patternSupplier, final long timeout,
                                      final long refreshInterval) {
    Ensure.suppliesNotNull(patternSupplier);
    Ensure.notNegative(timeout);
    Ensure.greater(refreshInterval, 0);

    return waitUntilOptionalIsPresent(() -> getScreen().positionOf(patternSupplier),
        timeout, refreshInterval);
  }

  public Future<Optional<Position>> waitUntilAsync(final Supplier<Image> patternSupplier,
                                                   final long timeout) {
    return waitUntilAsync(patternSupplier, timeout, getRefreshInterval());
  }

  public Future<Optional<Position>> waitUntilAsync(final Supplier<Image> patternSupplier,
                                                   final long timeout, final long refreshInterval) {
    return getExecutor().submit(() -> waitUntil(patternSupplier, timeout, refreshInterval));
  }

  /*
   *
   * waitWhile
   *
   */

  public Optional<Position> waitWhile(final Supplier<Image> patternSupplier, final long timeout) {
    return waitWhile(patternSupplier, timeout, getRefreshInterval());
  }

  public Optional<Position> waitWhile(final Supplier<Image> patternSupplier,
                                      final long timeout, final long refreshInterval) {
    Ensure.suppliesNotNull(patternSupplier);
    Ensure.notNegative(timeout);
    Ensure.greater(refreshInterval, 0);

    Supplier<Optional<Position>> supplier = () -> getScreen().positionOf(patternSupplier);
    Predicate<Position> check = position -> getScreen().imageAt(patternSupplier, position);

    return waitWhileOptionalIsPresent(supplier, check, timeout, refreshInterval);
  }

  public Future<Optional<Position>> waitWhileAsync(final Supplier<Image> patternSupplier,
                                                   final long timeout) {
    return waitWhileAsync(patternSupplier, timeout, getRefreshInterval());
  }

  public Future<Optional<Position>> waitWhileAsync(final Supplier<Image> patternSupplier,
                                                   final long timeout, final long refreshInterval) {
    return getExecutor().submit(() -> waitWhile(patternSupplier, timeout, refreshInterval));
  }

  /*
   *
   * waitUntilOne
   *
   */

  public Optional<Position> waitUntilOne(final List<Supplier<Image>> patternSuppliers,
                                         final long timeout) {
    return waitUntilOne(patternSuppliers, timeout, getRefreshInterval());
  }

  public Optional<Position> waitUntilOne(final List<Supplier<Image>> patternSuppliers,
                                         final long timeout, final long refreshInterval) {
    Ensure.containsNoNull(patternSuppliers);
    patternSuppliers.forEach(Ensure::suppliesNotNull);
    Ensure.notNegative(timeout);
    Ensure.greater(refreshInterval, 0);

    return waitUntilOptionalIsPresent(() -> getScreen().positionOf(patternSuppliers),
        timeout, refreshInterval);
  }

  public Future<Optional<Position>> waitUntilOneAsync(final List<Supplier<Image>> patternSuppliers,
                                                   final long timeout) {
    return waitUntilOneAsync(patternSuppliers, timeout, getRefreshInterval());
  }

  public Future<Optional<Position>> waitUntilOneAsync(final List<Supplier<Image>> patternSuppliers,
                                                   final long timeout, final long refreshInterval) {
    return getExecutor().submit(() -> waitUntilOne(patternSuppliers, timeout, refreshInterval));
  }
}
