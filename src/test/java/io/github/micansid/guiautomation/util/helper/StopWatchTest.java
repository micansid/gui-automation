package io.github.micansid.guiautomation.util.helper;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StopWatchTest {
  @Test
  void notRunningAndNotStoppedBeforeStart() {
    StopWatch sut = new StopWatch();
    assertThat(sut.isRunning()).isFalse();
    assertThat(sut.isStopped()).isFalse();
  }

  @Test
  void noStopBeforeStart() {
    StopWatch sut = new StopWatch();
    assertThat(sut.stop()).isFalse();
  }

  @Test
  void noPauseBeforeStart() {
    StopWatch sut = new StopWatch();
    assertThat(sut.pause()).isFalse();
  }

  @Test
  void notRunningAndStoppedAfterStop() {
    StopWatch sut = new StopWatch();
    sut.start();
    sut.stop();
    assertThat(sut.isRunning()).isFalse();
    assertThat(sut.isStopped()).isTrue();
  }


  @Test
  void simpleTimeDifference() {
    Supplier<Long> clock = (Supplier<Long>) mock(Supplier.class);
    when(clock.get()).thenReturn(1L).thenReturn(2L);

    StopWatch sut = new StopWatch(clock);
    assertThat(sut.start()).isTrue();
    assertThat(sut.stop()).isTrue();
    assertThat(sut.duration()).isEqualTo(1);
    assertThat(sut.pauses()).isEqualTo(0);
  }

  @Test
  void timeDifferenceWithPause() {
    Supplier<Long> clock = (Supplier<Long>) mock(Supplier.class);
    when(clock.get()).thenReturn(1L).thenReturn(2L).thenReturn(3L).thenReturn(4L);

    StopWatch sut = new StopWatch(clock);
    assertThat(sut.start()).isTrue();
    assertThat(sut.pause()).isTrue();
    assertThat(sut.pauses()).isEqualTo(1);
    assertThat(sut.start()).isTrue();
    assertThat(sut.stop()).isTrue();
    assertThat(sut.duration()).isEqualTo(2);
    assertThat(sut.pauses()).isEqualTo(1);
  }

  @Test
  void timeDifferenceWithTwoPause() {
    Supplier<Long> clock = (Supplier<Long>) mock(Supplier.class);
    when(clock.get())
        .thenReturn(1L)
        .thenReturn(2L)
        .thenReturn(3L)
        .thenReturn(4L)
        .thenReturn(5L)
        .thenReturn(6L);

    StopWatch sut = new StopWatch(clock);
    assertThat(sut.start()).isTrue();
    assertThat(sut.pause()).isTrue();
    assertThat(sut.pauses()).isEqualTo(1);
    assertThat(sut.start()).isTrue();
    assertThat(sut.pause()).isTrue();
    assertThat(sut.pauses()).isEqualTo(2);
    assertThat(sut.start()).isTrue();
    assertThat(sut.stop()).isTrue();
    assertThat(sut.duration()).isEqualTo(3);
    assertThat(sut.pauses()).isEqualTo(2);
  }

  @Test
  void noSuccessfulStopAfterStop() {
    StopWatch sut = new StopWatch();
    sut.start();
    assertThat(sut.stop()).isTrue();
    assertThat(sut.stop()).isFalse();
  }

  @Test
  void noSuccessfulStartAfterStart() {
    StopWatch sut = new StopWatch();
    assertThat(sut.start()).isTrue();
    assertThat(sut.start()).isFalse();
  }

  @Test
  void currentDurationBeforeStop() {
    Supplier<Long> clock = (Supplier<Long>) mock(Supplier.class);
    when(clock.get()).thenReturn(1L).thenReturn(2L);
    StopWatch sut = new StopWatch(clock);

    sut.start();
    assertThat(sut.duration()).isEqualTo(1);
    assertThat(sut.pauses()).isEqualTo(0);
  }

  @Test
  void stopRealTime() throws InterruptedException {
    StopWatch sut = new StopWatch();
    sut.start();
    Thread.sleep(200);
    sut.stop();
    assertThat(sut.duration()).isCloseTo(200, Percentage.withPercentage(2.0));
  }

  @Test
  void pausesOfANotStartedStopWatch() {
    StopWatch sut = new StopWatch();
    assertThat(sut.pauses()).isEqualTo(0);
  }
}