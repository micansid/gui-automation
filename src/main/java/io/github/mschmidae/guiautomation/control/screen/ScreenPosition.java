package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.util.Ensure;

/**
 * Representation of a Position on a screen.
 */
public class ScreenPosition implements Comparable<ScreenPosition> {
    private final int x;
    private final int y;

    /**
     * Creates a ScreenPosition when the parameter are not negative.
     * @param x x-coordinate, is not allowed to be negative
     * @param y y-coordinate, is not allowed to be negative
     */
    public ScreenPosition(final int x, final int y) {
        Ensure.notNegative(x);
        Ensure.notNegative(y);
        this.x = x;
        this.y = y;
    }

    /**
     * Add the sub position to the position.
     * @param position sub position
     * @return a new ScreenPosition with the summarized coordinates.
     */
    public ScreenPosition addSubPosition(final ScreenPosition position) {
        Ensure.notNull(position);
        return move(position.getX(), position.getY());
    }

    /**
     * Move the position by the values of the parameters. The summarized coordinates are not allowed to be negative.
     * @param moveX value to move x-coordinate
     * @param moveY value to move y-coordinate
     * @return a new ScreenPosition with the summarized coordinates.
     */
    public ScreenPosition move(final int moveX, final int moveY) {
        return new ScreenPosition(getX() + moveX, getY() + moveY);
    }

    /**
     * First criteria is the row number, second criteria is the column number.
     *
     * @param screenPosition other position
     * @return result of comparision
     */
    @Override
    public int compareTo(final ScreenPosition screenPosition) {
        Ensure.notNull(screenPosition);
        int result = getY() - screenPosition.getY();
        if (result == 0) {
            result = getX() - screenPosition.getX();
        }
        return result;
    }

    @Override
    public String toString() {
        return "ScreenPosition(" + getX() + "|" + getY() + ")";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScreenPosition that = (ScreenPosition) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
