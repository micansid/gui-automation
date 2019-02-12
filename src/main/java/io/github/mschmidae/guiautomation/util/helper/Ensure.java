package io.github.mschmidae.guiautomation.util.helper;

import io.github.mschmidae.guiautomation.util.image.Image;

import java.util.Collection;
import java.util.function.Supplier;

public final class Ensure {
  private Ensure() {
  }


  /**
   * Check: number >= 0.
   * @param number to check
   */
  public static void notNegative(final long number) {
    if (number < 0) {
      throw new IllegalArgumentException("ENSURE: " + number + " is negative");
    }
  }


  /**
   * Check: number >= comparison.
   * @param number to check
   * @param comparison comparison number
   */
  public static void greaterOrEqual(final long number, final long comparison) {
    if (number < comparison) {
      throw new IllegalArgumentException("ENSURE: " + number + " is not greater or equal than "
          + comparison);
    }
  }


  /**
   * Check: number > comparison.
   * @param number to check
   * @param comparison comparison number
   */
  public static void greater(final long number, final long comparison) {
    if (number <= comparison) {
      throw new IllegalArgumentException("ENSURE: " + number + " is not greater than "
          + comparison);
    }
  }


  /**
   * Check: number <= comparison.
   * @param number to check
   * @param comparison comparison number
   */
  public static void smallerOrEqual(final long number, final long comparison) {
    if (number > comparison) {
      throw new IllegalArgumentException("ENSURE: " + number + " is not smaller or equal than "
          + comparison);
    }
  }


  /**
   * Check: number < comparison.
   * @param number to check
   * @param comparison comparison number
   */
  public static void smaller(final long number, final long comparison) {
    if (number >= comparison) {
      throw new IllegalArgumentException("ENSURE: " + number + " is not smaller than "
          + comparison);
    }
  }

  /**
   * Check: number == comparison.
   * @param number to check
   * @param comparison comparison number
   */
  public static void equal(final long number, final long comparison) {
    if (number != comparison) {
      throw new IllegalArgumentException("ENSURE: " + number + " is not equal to " + comparison);
    }
  }

  /**
   * Check that Object isn't null.
   * @param object to check
   */
  public static void notNull(final Object object) {
    if (object == null) {
      throw new IllegalArgumentException("ENSURE: null is not allowed");
    }
  }


  /**
   * Check that String isn't null of a blank.
   * @param string to check
   */
  public static void notBlank(final String string) {
    notNull(string);
    if (string.isEmpty()) {
      throw new IllegalArgumentException("ENSURE: blank string is not allowed");
    }
  }


  /**
   * Check that array isn't empty.
   * @param array to check
   */
  public static void notEmpty(final Object[] array) {
    notNull(array);
    if (array.length <= 0) {
      throw new IllegalArgumentException("ENSURE: an empty array is not allowed");
    }
  }


  /**
   * Check that collection isn't empty.
   * @param collection to check
   * @param <T> type of collection
   */
  public static <T> void notEmpty(final Collection<T> collection) {
    notNull(collection);
    if (collection.size() <= 0) {
      throw new IllegalArgumentException("ENSURE: a empty collection is not allowed");
    }
  }


  /**
   * Check that array isn't null and contains no null.
   * @param array to check
   */
  public static void containsNoNull(final Object[] array) {
    notNull(array);
    for (Object element : array) {
      notNull(element);
    }
  }


  /**
   * Check that collection isn't null and contains no null.
   * @param collection to check
   * @param <T> type of collection
   */
  public static <T> void containsNoNull(final Collection<T> collection) {
    notNull(collection);
    for (T element : collection) {
      notNull(element);
    }
  }


  /**
   * Check that supplier is not null and supplies not null.
   * @param supplier to check
   * @param <T> supplied type
   */
  public static <T> void suppliesNotNull(final Supplier<T> supplier) {
    Ensure.notNull(supplier);
    Ensure.notNull(supplier.get());
  }
}
