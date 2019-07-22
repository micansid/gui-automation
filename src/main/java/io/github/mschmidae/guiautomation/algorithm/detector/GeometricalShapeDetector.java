package io.github.mschmidae.guiautomation.algorithm.detector;

import io.github.mschmidae.guiautomation.util.Section;
import io.github.mschmidae.guiautomation.util.image.Image;
import java.util.List;

public interface GeometricalShapeDetector {
  List<Section> detectRectangles(Image image);
}
