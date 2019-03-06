package io.github.mschmidae.guiautomation.algorithm.find;

import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.helper.Ensure;
import io.github.mschmidae.guiautomation.util.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_string-search_algorithm
 */
public class BadCharacterFinder implements ImagePositionFinder {
  @Override
  public Optional<Position> find(Image image, Image pattern) {
    Ensure.notNull(image);
    Ensure.notNull(pattern);

    BadCharacterPattern badCharacterPattern = new BadCharacterPattern(pattern);
    Optional<Position> result = Optional.empty();
    boolean found = false;

    for (int y = 0; y < image.getHeight() && !found; y++) {
      int x = 0;

      while (x < image.getWidth() && !found) {
        int shift = badCharacterShift(image, badCharacterPattern, x, y);
        if (shift == 0) {
          found = at(image, pattern, x, y);
          if (found) {
            result = Optional.of(new Position(x, y));
          } else {
            x++;
          }
        } else {
          x += shift;
        }
      }
    }

    return result;
  }

  @Override
  public List<Position> findAll(Image image, Image pattern) {
    Ensure.notNull(image);
    Ensure.notNull(pattern);

    BadCharacterPattern badCharacterPattern = new BadCharacterPattern(pattern);

    return IntStream.range(0, image.getHeight()).parallel().boxed().flatMap(y -> {
      List<Position> positions = new ArrayList<>();
      int x = 0;
      while (x < image.getWidth()) {
        int shift = badCharacterShift(image, badCharacterPattern, x, y);
        if (shift == 0) {
          if (at(image, pattern, x, y)) {
            positions.add(new Position(x, y));
          }
          x++;
        } else {
          x += shift;
        }
      }
      return positions.stream();
    }).sorted().collect(Collectors.toList());
  }

  protected int badCharacterShift(final Image image, final BadCharacterPattern pattern, final int positionX, final int positionY) {
    int shift = 0;

    if (image.getWidth() >= positionX + pattern.getWidth() && image.getHeight() >= positionY + pattern.getHeight()) {
      for (int x = pattern.getWidth() - 1; x >= 0 && shift == 0; x--) {
        int color = image.getRgb(positionX + x, positionY + pattern.getLineIndex());
        if (color != pattern.getRgb(x, pattern.getLineIndex()) && !pattern.isTransparent(x, pattern.getLineIndex())) {
          if (pattern.getColorDelta().containsKey(color)) {
            if (x == pattern.getWidth() - 1) {
              shift = pattern.getColorDelta().get(color);
            } else {
              shift = pattern.getColorDelta().get(color) - (pattern.getWidth() - x);
              if (shift <= 0) {
                shift = 1;
              }
            }

          } else {
            if (pattern.containsTransparent()) {
              shift = pattern.getTransparentOffset();
            } else {
              shift = x;
            }
          }
        }
      }
    } else {
      shift = pattern.getWidth();
    }
    Ensure.notNegative(shift);
    return shift;
  }
}
