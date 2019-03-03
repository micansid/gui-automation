package io.github.mschmidae.guiautomation.algorithm.find;

import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.image.Image;
import java.util.List;
import java.util.Optional;

/**
 * https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_string-search_algorithm
 */
public class BadCharacterFinder implements ImagePositionFinder {
  @Override
  public Optional<Position> find(Image image, Image pattern) {
    return Optional.empty();
  }

  @Override
  public List<Position> findAll(Image image, Image pattern) {
    return null;
  }
}
