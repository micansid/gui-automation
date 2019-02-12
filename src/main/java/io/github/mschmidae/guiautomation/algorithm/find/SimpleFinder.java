package io.github.mschmidae.guiautomation.algorithm.find;

import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.helper.Ensure;
import io.github.mschmidae.guiautomation.util.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleFinder implements ImagePositionFinder {
  @Override
  public Optional<Position> find(final Image image, final Image pattern) {
    Ensure.notNull(image);
    Ensure.notNull(pattern);

    Optional<Position> result = Optional.empty();
    boolean found = false;

    for (int y = 0; y < image.getHeight() && !found; y++) {
      for (int x = 0; x < image.getWidth() && !found; x++) {
        if (at(image, pattern, x, y)) {
          result = Optional.of(new Position(x, y));
          found = true;
        }
      }
    }

    return result;
  }

  @Override
  public List<Position> findAll(final Image image, final Image pattern) {
    Ensure.notNull(image);
    Ensure.notNull(pattern);
    List<Position> result = new ArrayList<>();

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        if (at(image, pattern, x, y)) {
          result.add(new Position(x, y));
        }
      }
    }

    return result;
  }
}
