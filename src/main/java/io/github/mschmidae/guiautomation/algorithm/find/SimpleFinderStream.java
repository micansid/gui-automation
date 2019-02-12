package io.github.mschmidae.guiautomation.algorithm.find;

import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.helper.Ensure;
import io.github.mschmidae.guiautomation.util.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleFinderStream implements ImagePositionFinder {
  @Override
  public Optional<Position> find(final Image image, final Image pattern) {
    Ensure.notNull(image);
    Ensure.notNull(pattern);

    return IntStream.range(0, image.getHeight()).parallel().mapToObj(y -> {
      Position result = null;
      boolean found = false;
      for (int x = 0; x < image.getWidth() && !found; x++) {
        if (at(image, pattern, x, y)) {
          result = new Position(x, y);
          found = true;
        }
      }
      return result;
    }).filter(position -> position != null).sorted().findFirst();
  }

  @Override
  public List<Position> findAll(final Image image, final Image pattern) {
    Ensure.notNull(image);
    Ensure.notNull(pattern);

    return IntStream.range(0, image.getHeight()).parallel().boxed().flatMap(y -> {
      List<Position> positions = new ArrayList<>();
      for (int x = 0; x < image.getWidth(); x++) {
        if (at(image, pattern, x, y)) {
          positions.add(new Position(x, y));
        }
      }
      return positions.stream();
    }).sorted().collect(Collectors.toList());
  }
}
