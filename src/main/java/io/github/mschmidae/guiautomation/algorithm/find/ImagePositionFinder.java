package io.github.mschmidae.guiautomation.algorithm.find;

import io.github.mschmidae.guiautomation.util.helper.Ensure;
import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.image.Image;

import java.util.*;


/**
 * Interface of ImagePositionFinders, which provide methods to find the position of images in a image.
 */
public interface ImagePositionFinder {

    /**
     * Find first position of the pattern in the image.
     *
     * @param image   to search in
     * @param pattern which should be found in the image
     * @return Position where the pattern is located in the image or an empty Optional if the pattern is not in the image.
     */
    Optional<Position> find(Image image, Image pattern);

    /**
     * Find all positions of the pattern in the image.
     *
     * @param image   to search in
     * @param pattern which should be found in the image
     * @return sorted list of all ScreenPositions of the pattern or an empty list if the pattern is not in the image.
     */
    List<Position> findAll(Image image, Image pattern);

    /**
     * Final all positions of the set of patterns in the image.
     *
     * @param image    to search in
     * @param patterns set of pattern which should be found in the image
     * @return a mapping of the pattern an a list of the ScreenPositions. If a pattern is not in the image, then is the list empty.
     */
    default Map<Image, List<Position>> findAll(Image image, Set<Image> patterns) {
        Ensure.notNull(image);
        Ensure.notNull(patterns);

        Map<Image, List<Position>> result = new HashMap<>();
        for (Image pattern : patterns) {
            result.put(pattern, findAll(image, pattern));
        }
        return result;
    }

    /**
     * Check is the pattern is at the position in the image.
     *
     * @param image    where the image should be in
     * @param pattern  which should be at the position
     * @param position where the pattern should be in the image
     * @return true when the pattern is at the position in the image
     */
    default boolean at(final Image image, final Image pattern, final Position position) {
        Ensure.notNull(image);
        Ensure.notNull(pattern);
        Ensure.notNull(position);
        return at(image, pattern, position.getX(), position.getY());
    }

    /**
     * Check is the pattern is at the position in the image.
     *
     * @param image     where the image should be in
     * @param pattern   which should be at the position
     * @param positionX x-coordinate where the pattern should be in the image
     * @param positionY y-coordinate where the pattern should be in the image
     * @return true when the pattern is at the position in the image
     */
    default boolean at(final Image image, final Image pattern, final int positionX, final int positionY) {
        Ensure.notNull(image);
        Ensure.notNull(pattern);
        Ensure.notNegative(positionX);
        Ensure.notNegative(positionY);
        Ensure.smaller(positionX, image.getWidth());
        Ensure.smaller(positionY, image.getHeight());
        boolean match = true;

        for (int y = 0; y < pattern.getHeight() && match; y++) {
            for (int x = 0; x < pattern.getWidth() && match; x++) {
                if (image.getWidth() <= positionX + x || image.getHeight() <= positionY + y
                        || (match && image.getRgb(positionX + x, positionY + y) != pattern.getRgb(x, y) && !pattern.isTransparent(x, y))) {
                    match = false;
                }
            }
        }
        return match;
    }
}