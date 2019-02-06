package io.github.mschmidae.guiautomation.util.helper;

import java.util.Collection;

public final class Ensure {
    private Ensure() {
    }

    public static void notNegative(final long number) {
        if (number < 0) {
            throw new IllegalArgumentException("ENSURE: " + number + " is negative");
        }
    }

    public static void greaterOrEqual(final long number, final long comparison) {
        if (number < comparison) {
            throw new IllegalArgumentException("ENSURE: " + number + " is not greater or equal than " + comparison);
        }
    }

    public static void greater(final long number, final long comparison) {
        if (number <= comparison) {
            throw new IllegalArgumentException("ENSURE: " + number + " is not greater than " + comparison);
        }
    }

    public static void smallerOrEqual(final long number, final long comparison) {
        if (number > comparison) {
            throw new IllegalArgumentException("ENSURE: " + number + " is not smaller or equal than " + comparison);
        }
    }

    public static void smaller(final long number, final long comparison) {
        if (number >= comparison) {
            throw new IllegalArgumentException("ENSURE: " + number + " is not smaller than " + comparison);
        }
    }

    public static void equal(final long number, final long comparison) {
        if (number != comparison) {
            throw new IllegalArgumentException("ENSURE: " + number + " is not equal to " + comparison);
        }
    }

    public static void notNull(final Object object) {
        if (object == null) {
            throw new IllegalArgumentException("ENSURE: null is not allowed");
        }
    }

    public static void notBlank(final String string) {
        notNull(string);
        if (string.isEmpty()) {
            throw new IllegalArgumentException("ENSURE: blank string is not allowed");
        }
    }

    public static void notEmpty(final Object[] array) {
        notNull(array);
        if (array.length <= 0) {
            throw new IllegalArgumentException("ENSURE: an empty array is not allowed");
        }
    }

    public static void containsNoNull(final Object[] array) {
        notNull(array);
        for (Object element : array) {
            notNull(element);
        }
    }

    public static <T> void notEmpty(final Collection<T> collection) {
        notNull(collection);
        if (collection.size() <= 0) {
            throw new IllegalArgumentException("ENSURE: a empty collection is not allowed");
        }
    }

    public static <T> void containsNoNull(final Collection<T> collection) {
        notNull(collection);
        for (T element : collection) {
            notNull(element);
        }
    }
}
