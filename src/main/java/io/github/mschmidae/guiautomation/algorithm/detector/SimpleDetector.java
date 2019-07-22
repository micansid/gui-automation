package io.github.mschmidae.guiautomation.algorithm.detector;

import io.github.mschmidae.guiautomation.util.Section;
import io.github.mschmidae.guiautomation.util.image.Image;
import java.util.List;

public class SimpleDetector implements GeometricalShapeDetector {
  @Override
  public List<Section> detectRectangles(Image image) {
    int lastRgb = 0;
    int length = 0;
    for (int i = 0; i < image.getWidth(); i++) {
      if (image.getRgb(i, 0) == lastRgb) {
        length ++;
      } else {
        System.out.println(lastRgb + " - " + length);
        length = 1;
      }
      lastRgb = image.getRgb(i, 0);
    }
    System.out.println(lastRgb + " - " + length);
    return null;
  }
}
