package io.github.micansid.guiautomation.util.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class StopWatch {
  private final Supplier<Long> clock;
  @Getter(AccessLevel.PUBLIC)
  private boolean running = false;
  @Getter(AccessLevel.PUBLIC)
  private boolean stopped = false;
  private final List<Long> starts = new ArrayList<>();
  private final List<Long> stops = new ArrayList<>();


  /**
   * StopWatch which stops the time in milliseconds as time unit. System::currentTimeMillis as time
   * supplier.
   */
  public StopWatch() {
    this(System::currentTimeMillis);
  }


  /**
   * StopWatch with time units according the supplier.
   * @param clock supplier of a timing counter
   */
  public StopWatch(final Supplier<Long> clock) {
    this.clock = clock;
  }


  /**
   * Start the StopWatch. When the StopWatch is still running or stopped nothing will happen.
   * @return result of the start action
   */
  public synchronized boolean start() {
    boolean result = false;
    if (!isRunning() && !isStopped()) {
      getStarts().add(getClock().get());
      setRunning(true);
      result = true;
    }
    return result;
  }


  /**
   * Pause the StopWatch. When the StopWatch isn't running nothing will happen.
   * @return result of the pause action
   */
  public synchronized boolean pause() {
    boolean result = false;
    if (isRunning()) {
      getStops().add(getClock().get());
      setRunning(false);
      result = true;
    }
    return result;
  }


  /**
   * Stop the StopWatch. When the StopWatch isn't running nothing will happen.
   * @return result of the stop action
   */
  public synchronized boolean stop() {
    boolean result = false;
    if (isRunning()) {
      getStops().add(getClock().get());
      setRunning(false);
      setStopped(true);
      result = true;
    }
    return result;
  }


  /**
   * Duration of time units.
   * @return duration of time units
   */
  public synchronized long duration() {
    long result = 0;

    for (int index = 0; index < getStarts().size(); index++) {
      if (index < getStops().size()) {
        result += getStops().get(index) - getStarts().get(index);
      } else {
        result += getClock().get() - getStarts().get(index);
      }
    }

    return result;
  }

  /**
   * Number of pauses.
   * @return numbers of pauses
   */
  public synchronized int pauses() {
    int result = 0;
    if (isStopped()) {
      result = getStops().size() - 1;
    } else {
      result = getStops().size();
    }
    return result;
  }
}
