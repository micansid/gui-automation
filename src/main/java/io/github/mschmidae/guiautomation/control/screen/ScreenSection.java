package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.util.Ensure;
import lombok.Getter;

/**
 * Representation section on the screen.
 */
@Getter
public class ScreenSection {
    /**
     * Left upper bound of the section.
     */
    private final ScreenPosition startPosition;

    /**
     * Right lower bound of the section.
     */
    private final ScreenPosition endPosition;

    /**
     * Constructs a ScreenSection based on the two ScreenPositions.
     * @param startPosition left upper bound
     * @param endPosition right lower bound
     */
    public ScreenSection(final ScreenPosition startPosition, final ScreenPosition endPosition) {
        Ensure.notNull(startPosition);
        Ensure.notNull(endPosition);
        Ensure.greaterOrEqual(endPosition.getX(), startPosition.getX());
        Ensure.greaterOrEqual(endPosition.getY(), startPosition.getY());

        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    /**
     * Constructs a ScreenSection based on the left upper bound, the width and the height.
     * @param startPosition left upper bound
     * @param width of the section, have to be greater than 0
     * @param height of the section, have to be greater than 0
     */
    public ScreenSection(final ScreenPosition startPosition, final int width, final int height) {
        Ensure.notNull(startPosition);
        Ensure.greaterOrEqual(width, 1);
        Ensure.greaterOrEqual(height, 1);

        this.startPosition = startPosition;
        this.endPosition = new ScreenPosition(startPosition.getX() + width - 1, startPosition.getY() + height - 1);
    }

    /**
     * Calculates the ScreenPosition of the given sub position. The start position of the section is set as origin.
     * @param position to scale up
     * @return the position out of the section
     */
    public ScreenPosition scaleUpPosition(final ScreenPosition position) {
        Ensure.notNull(position);
        Ensure.smaller(position.getX(), getWidth());
        Ensure.smaller(position.getY(), getHeight());

        return new ScreenPosition(getStartPosition().getX() + position.getX(), getStartPosition().getY() + position.getY());
    }

    public int getWidth() {
        return getEndPosition().getX() - getStartPosition().getX() + 1;
    }

    public int getHeight() {
        return getEndPosition().getY() - getStartPosition().getY() + 1;
    }
}