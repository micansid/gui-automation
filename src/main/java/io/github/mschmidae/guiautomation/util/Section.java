package io.github.mschmidae.guiautomation.util;

import io.github.mschmidae.guiautomation.util.helper.Ensure;
import lombok.Getter;

/**
 * Representation section on the screen.
 */
@Getter
public class Section {
    /**
     * Left upper bound of the section.
     */
    private final Position startPosition;

    /**
     * Right lower bound of the section.
     */
    private final Position endPosition;

    /**
     * Constructs a Section based on the two ScreenPositions.
     * @param startPosition left upper bound
     * @param endPosition right lower bound
     */
    public Section(final Position startPosition, final Position endPosition) {
        Ensure.notNull(startPosition);
        Ensure.notNull(endPosition);
        Ensure.greaterOrEqual(endPosition.getX(), startPosition.getX());
        Ensure.greaterOrEqual(endPosition.getY(), startPosition.getY());

        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    /**
     * Constructs a Section based on the left upper bound, the width and the height.
     * @param startPosition left upper bound
     * @param width of the section, have to be greater than 0
     * @param height of the section, have to be greater than 0
     */
    public Section(final Position startPosition, final int width, final int height) {
        Ensure.notNull(startPosition);
        Ensure.greaterOrEqual(width, 1);
        Ensure.greaterOrEqual(height, 1);

        this.startPosition = startPosition;
        this.endPosition = new Position(startPosition.getX() + width - 1, startPosition.getY() + height - 1);
    }

    /**
     * Calculates the Position of the given sub position. The start position of the section is set as origin.
     * @param position to scale up
     * @return the position out of the section
     */
    public Position scaleUpPosition(final Position position) {
        Ensure.notNull(position);
        Ensure.smaller(position.getX(), getWidth());
        Ensure.smaller(position.getY(), getHeight());

        return new Position(getStartPosition().getX() + position.getX(), getStartPosition().getY() + position.getY());
    }

    public int getWidth() {
        return getEndPosition().getX() - getStartPosition().getX() + 1;
    }

    public int getHeight() {
        return getEndPosition().getY() - getStartPosition().getY() + 1;
    }
}