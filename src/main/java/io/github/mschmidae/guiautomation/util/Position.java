package io.github.mschmidae.guiautomation.util;

import io.github.mschmidae.guiautomation.util.helper.Ensure;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Representation of a Position on a screen.
 */
@EqualsAndHashCode
@Getter
public class Position implements Comparable<Position> {
  private final int x;
  private final int y;

  /**
   * Creates a Position when the parameter are not negative.
   * @param x x-coordinate, is not allowed to be negative
   * @param y y-coordinate, is not allowed to be negative
   */
  public Position(final int x, final int y) {
    Ensure.notNegative(x);
    Ensure.notNegative(y);
    this.x = x;
    this.y = y;
  }

  /**
   * Add the sub position to the position.
   * @param position sub position
   * @return a new Position with the summarized coordinates.
   */
  public Position addSubPosition(final Position position) {
    Ensure.notNull(position);
    return move(position.getX(), position.getY());
  }

  /**
   * Move the position by the values of the parameters. The summarized coordinates are not allowed
   * to be negative.
   * @param moveX value to move x-coordinate
   * @param moveY value to move y-coordinate
   * @return a new Position with the summarized coordinates.
   */
  public Position move(final int moveX, final int moveY) {
    return new Position(getX() + moveX, getY() + moveY);
  }

  /**
   * First criteria is the row number, second criteria is the column number.
   *
   * @param position other position
   * @return result of comparision
   */
  @Override
  public int compareTo(final Position position) {
    Ensure.notNull(position);
    int result = getY() - position.getY();
    if (result == 0) {
      result = getX() - position.getX();
    }
    return result;
  }

  @Override
  public String toString() {
    return "Position(" + getX() + "|" + getY() + ")";
  }
}
