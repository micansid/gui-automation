package io.github.mschmidae.guiautomation.algorithm.detector;

import io.github.mschmidae.guiautomation.TestData;
import io.github.mschmidae.guiautomation.util.Section;
import io.github.mschmidae.guiautomation.util.image.Image;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import static org.assertj.core.api.Assertions.assertThat;

class GeometricalShapeDetectorTest {
  private static Stream<GeometricalShapeDetector> detectorProvider() {
    return Stream.of(
        new SimpleDetector());
  }

  @ParameterizedTest
  @MethodSource("detectorProvider")
  void detectRectangleOfButton(final GeometricalShapeDetector detector) {
    TestData testData = TestData.BUTTON_CANCEL;
    Image button = testData.getImage();
    Section section = new Section(testData.getPositions().get(0), button.getWidth(), button.getHeight());
    List<Section> result = detector.detectRectangles(button);

    assertThat(result).containsExactly(section);
  }

}