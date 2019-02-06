package io.github.mschmidae.guiautomation.util.helper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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

    public StopWatch() {
        this(System::currentTimeMillis);
    }

    public StopWatch(final Supplier<Long> clock) {
        this.clock = clock;
    }

    public synchronized boolean start() {
        boolean result = false;
        if (!isRunning() && !isStopped()) {
            getStarts().add(getClock().get());
            setRunning(true);
            result = true;
        }
        return result;
    }

    public synchronized boolean pause() {
        boolean result = false;
        if (isRunning()) {
            getStops().add(getClock().get());
            setRunning(false);
            result = true;
        }
        return result;
    }

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
