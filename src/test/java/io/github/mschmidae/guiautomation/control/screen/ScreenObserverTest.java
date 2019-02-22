package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.function.TriFunction;
import io.github.mschmidae.guiautomation.util.image.Image;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScreenObserverTest {
  private final static Optional<Position> BAD_CASE = Optional.empty();
  private final static Optional<Position> GOOD_CASE = Optional.of(new Position(0, 0));

  private static Stream<TriFunction<Optional<Position>, ScreenObserver,
      Supplier<Image>, Integer>> waitUntilProvider() {
    return Stream.of(ScreenObserver::waitUntil,
        (observer, supplier, timeout) -> {
          Optional<Position> result;
          try {
            result = observer.waitUntilAsync(supplier, timeout).get();
          } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
          }
          return result;
        },
        (observer, supplier, timeout) -> observer.waitUntilOne(Arrays.asList(supplier), timeout),
        (observer, supplier, timeout) -> {
          Optional<Position> result;
          try {
            result = observer.waitUntilOneAsync(Arrays.asList(supplier), timeout).get();
          } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
          }
          return result;
        });
  }

  private static Stream<TriFunction<Optional<Position>, ScreenObserver,
      Supplier<Image>, Integer>> waitWhileProvider() {
    return Stream.of(ScreenObserver::waitWhile,
        (observer, supplier, timeout) -> {
          Optional<Position> result;
          try {
            result = observer.waitWhileAsync(supplier, timeout).get();
          } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
          }
          return result;
        });
  }

  @ParameterizedTest
  @MethodSource("waitUntilProvider")
  void waitUntilScreenImageIsStillPresent(final TriFunction<Optional<Position>, ScreenObserver,
      Supplier<Image>, Integer> waitUntil) {
    Screen screen = mock(Screen.class);
    when(screen.positionOf(any(Supplier.class))).thenReturn(GOOD_CASE);
    when(screen.positionOf(any(List.class))).thenReturn(GOOD_CASE);
    assertWaitUntil(waitUntil, screen, 2, true);
  }

  @ParameterizedTest
  @MethodSource("waitUntilProvider")
  void waitUntilScreenImageIsNotPresent(final TriFunction<Optional<Position>, ScreenObserver,
      Supplier<Image>, Integer> waitUntil) {
    Screen screen = mock(Screen.class);
    when(screen.positionOf(any(Supplier.class))).thenReturn(BAD_CASE);
    when(screen.positionOf(any(List.class))).thenReturn(BAD_CASE);
    assertWaitUntil(waitUntil, screen, 2, false);
  }

  @ParameterizedTest
  @MethodSource("waitUntilProvider")
  void waitUntilScreenImageIsPresentOneSecondTry(final TriFunction<Optional<Position>,
      ScreenObserver, Supplier<Image>, Integer> waitUntil) {
    Screen screen = mock(Screen.class);
    when(screen.positionOf(any(Supplier.class)))
        .thenReturn(BAD_CASE)
        .thenReturn(GOOD_CASE);
    when(screen.positionOf(any(List.class)))
        .thenReturn(BAD_CASE)
        .thenReturn(GOOD_CASE);
    assertWaitUntil(waitUntil, screen, 2, true);
  }

  @ParameterizedTest
  @MethodSource("waitUntilProvider")
  void waitUntilScreenImageIsPresentAtLastCheckBeforeTimeout(final TriFunction<Optional<Position>,
      ScreenObserver, Supplier<Image>, Integer> waitUntil) {
    Screen screen = mock(Screen.class);
    when(screen.positionOf(any(Supplier.class)))
        .thenReturn(BAD_CASE, BAD_CASE)
        .thenReturn(GOOD_CASE);
    when(screen.positionOf(any(List.class)))
        .thenReturn(BAD_CASE, BAD_CASE)
        .thenReturn(GOOD_CASE);
    assertWaitUntil(waitUntil, screen, 2, true);
  }

  @ParameterizedTest
  @MethodSource("waitUntilProvider")
  void waitUntilScreenImageIsPresentAfterTimeout(final TriFunction<Optional<Position>,
      ScreenObserver, Supplier<Image>, Integer> waitUntil) {
    Screen screen = mock(Screen.class);
    when(screen.positionOf(any(Supplier.class)))
        .thenReturn(BAD_CASE, BAD_CASE, BAD_CASE)
        .thenReturn(GOOD_CASE);
    when(screen.positionOf(any(List.class)))
        .thenReturn(BAD_CASE, BAD_CASE, BAD_CASE)
        .thenReturn(GOOD_CASE);
    assertWaitUntil(waitUntil, screen, 2, false);
  }

  @ParameterizedTest
  @MethodSource("waitWhileProvider")
  void waitWhileScreenImageIsNotPresent(final TriFunction<Optional<Position>, ScreenObserver,
      Supplier<Image>, Integer> waitWhile) {
    Screen screen = mock(Screen.class);
    when(screen.positionOf(any(Supplier.class))).thenReturn(BAD_CASE);
    assertWaitWhile(waitWhile, screen, 2, true);
  }

  @ParameterizedTest
  @MethodSource("waitWhileProvider")
  void waitWhileScreenImageIsPresentAtTheTimeout(final TriFunction<Optional<Position>,
      ScreenObserver, Supplier<Image>, Integer> waitWhile) {
    Screen screen = mock(Screen.class);
    when(screen.positionOf(any(Supplier.class))).thenReturn(GOOD_CASE);
    when(screen.imageAt(any(Supplier.class), eq(GOOD_CASE.get())))
        .thenReturn(true);
    assertWaitWhile(waitWhile, screen, 2, false);
  }

  @ParameterizedTest
  @MethodSource("waitWhileProvider")
  void waitWhileScreenImageIsPresentAtFirstTry(final TriFunction<Optional<Position>, ScreenObserver,
      Supplier<Image>, Integer> waitWhile) {
    Screen screen = mock(Screen.class);
    when(screen.positionOf(any(Supplier.class))).thenReturn(GOOD_CASE, BAD_CASE);
    when(screen.imageAt(any(Supplier.class), eq(GOOD_CASE.get())))
        .thenReturn(true, false);
    assertWaitWhile(waitWhile, screen, 2, true);
  }

  @ParameterizedTest
  @MethodSource("waitWhileProvider")
  void waitWhileScreenImageIsPresentUntilTimeout(final TriFunction<Optional<Position>,
      ScreenObserver, Supplier<Image>, Integer> waitWhile) {
    Screen screen = mock(Screen.class);
    when(screen.positionOf(any(Supplier.class))).thenReturn(GOOD_CASE, BAD_CASE);
    when(screen.imageAt(any(Supplier.class), eq(GOOD_CASE.get())))
        .thenReturn(true, false);
    assertWaitWhile(waitWhile, screen, 2, true);
  }

  @ParameterizedTest
  @MethodSource("waitWhileProvider")
  void waitWhileScreenImageIsPresentAfterTimeout(final TriFunction<Optional<Position>,
      ScreenObserver, Supplier<Image>, Integer> waitWhile) {
    Screen screen = mock(Screen.class);
    when(screen.positionOf(any(Supplier.class))).thenReturn(GOOD_CASE);
    when(screen.imageAt(any(Supplier.class), eq(GOOD_CASE.get())))
        .thenReturn(true, true, false);
    assertWaitWhile(waitWhile, screen, 2, false);
  }

  private void assertWaitUntil(final TriFunction<Optional<Position>, ScreenObserver,
      Supplier<Image>, Integer> waitUntil, final Screen screen, final int timeout,
                               final boolean result) {
    Supplier<Long> clock = (Supplier<Long>) mock(Supplier.class);
    when(clock.get()).thenReturn(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L, 19L, 20L);
    ScreenObserver sut = new ScreenObserver(screen, clock, 1);

    if (result) {
      assertThat(waitUntil.apply(sut, () -> new Image(new int[]{0}, 1, 1), timeout))
          .isPresent().isEqualTo(GOOD_CASE);
    } else {
      assertThat(waitUntil.apply(sut, () -> new Image(new int[]{0}, 1, 1), timeout))
          .isNotPresent();
    }
  }

  private void assertWaitWhile(final TriFunction<Optional<Position>, ScreenObserver,
      Supplier<Image>, Integer> waitWhile, final Screen screen, final int timeout,
                               final boolean result) {
    Supplier<Long> clock = (Supplier<Long>) mock(Supplier.class);
    when(clock.get()).thenReturn(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L, 19L, 20L);

    ScreenObserver sut = new ScreenObserver(screen, clock, 1);

    if (result) {
      assertThat(waitWhile.apply(sut, () -> new Image(new int[]{0}, 1, 1), timeout))
          .isNotPresent();
    } else {
      assertThat(waitWhile.apply(sut, () -> new Image(new int[]{0}, 1, 1), timeout))
          .isPresent();
    }
  }
}