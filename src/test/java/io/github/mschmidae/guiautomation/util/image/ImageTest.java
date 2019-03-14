package io.github.mschmidae.guiautomation.util.image;

import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.Section;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;

class ImageTest {
  private static final BufferedImage IMAGE_COMMIT_BUTTON = new ImageLoader()
      .loadBufferedImageFromResources("intellij_button_commit.png")
      .orElseThrow(() -> new RuntimeException("The test image ist not available"));
  private static final BufferedImage IMAGE_CANCEL_BUTTON = new ImageLoader()
      .loadBufferedImageFromResources("intellij_button_cancel.png")
      .orElseThrow(() -> new RuntimeException("The test image ist not available"));

  @Test
  void returnsSameRGBWidthHeightAsBufferedImage() {
    Image sut = new Image(IMAGE_COMMIT_BUTTON);
    assertThat(sut.getWidth()).isEqualTo(IMAGE_COMMIT_BUTTON.getWidth());
    assertThat(sut.getHeight()).isEqualTo(IMAGE_COMMIT_BUTTON.getHeight());
    for (int y = 0; y < IMAGE_COMMIT_BUTTON.getHeight(); y++) {
      for (int x = 0; x < IMAGE_COMMIT_BUTTON.getWidth(); x++) {
        int bufferedImageRgb = IMAGE_COMMIT_BUTTON.getRGB(x, y);
        assertThat(sut.getRgb(x, y)).isEqualTo(bufferedImageRgb);
        assertThat(sut.getRgb(new Position(x, y))).isEqualTo(bufferedImageRgb);
        assertThat(sut.getRed(x, y)).isEqualTo(new Color(bufferedImageRgb, true).getRed());
        assertThat(sut.getRed(new Position(x, y)))
            .isEqualTo(new Color(bufferedImageRgb, true).getRed());
        assertThat(sut.getGreen(x, y)).isEqualTo(new Color(bufferedImageRgb, true).getGreen());
        assertThat(sut.getGreen(new Position(x, y)))
            .isEqualTo(new Color(bufferedImageRgb, true).getGreen());
        assertThat(sut.getBlue(x, y)).isEqualTo(new Color(bufferedImageRgb, true).getBlue());
        assertThat(sut.getBlue(new Position(x, y)))
            .isEqualTo(new Color(bufferedImageRgb, true).getBlue());
        assertThat(sut.getAlpha(x, y)).isEqualTo(new Color(bufferedImageRgb, true).getAlpha());
        assertThat(sut.getAlpha(new Position(x, y)))
            .isEqualTo(new Color(bufferedImageRgb, true).getAlpha());
      }
    }
  }

  @Test
  void twoImagesWithEqualBufferedImageAreEqual() {
    BufferedImage commit_button = new ImageLoader()
        .loadBufferedImageFromResources("intellij_button_commit.png")
        .orElseThrow(() -> new RuntimeException("The test image ist not available"));

    Image sut = new Image(commit_button);
    Image other = new Image(IMAGE_COMMIT_BUTTON);
    assertThat(sut).isEqualTo(other).hasSameHashCodeAs(other);
  }

  @Test
  void twoImagesWithDifferentBufferedImageAreNotEqual() {
    Image sut = new Image(IMAGE_CANCEL_BUTTON);
    Image other = new Image(IMAGE_COMMIT_BUTTON);
    assertThat(sut).isNotEqualTo(other);
  }

  @Test
  void immutableImageDidNotDependFromBufferedImage() {
    BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    bufferedImage.setRGB(0, 0, 0);

    Image sut = new Image(bufferedImage);
    assertThat(sut.getRgb(0, 0)).isEqualTo(0);

    bufferedImage.setRGB(0, 0, 1);
    assertThat(bufferedImage.getRGB(0, 0)).isEqualTo(1);
    assertThat(sut.getRgb(0, 0)).isEqualTo(0);

    BufferedImage result = sut.bufferedImage();
    result.setRGB(0, 0, 2);
    assertThat(result.getRGB(0, 0)).isEqualTo(2);
    assertThat(sut.getRgb(0, 0)).isEqualTo(0);
  }

  @Test
  void immutableImageDidNotDependFromIntArray() {
    int width = 1;
    int height = 1;
    int[] rgbData = new int[]{0};

    Image sut = new Image(rgbData, width, height);
    assertThat(sut.getRgb(0, 0)).isEqualTo(0);

    rgbData[0] = 1;
    assertThat(sut.getRgb(0, 0)).isEqualTo(0);

    int[] result = sut.getRgbData();
    result[0] = 2;
    assertThat(sut.getRgb(0, 0)).isEqualTo(0);
  }

  @Test
  void middleOfEvenImageWidthAndHeight() {
    Image sut = new Image(new int[]{0, 0, 0, 0, 0, 0, 0, 0}, 4, 2);
    assertThat(sut.middle()).isEqualTo(new Position(2, 1));
  }

  @Test
  void middleOfOddImageWidthAndHeight() {
    Image sut = new Image(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 3, 5);
    assertThat(sut.middle()).isEqualTo(new Position(1, 2));
  }

  @Test
  void rgbLineMatchesTheLineInTheImage() {
    Image sut = new Image(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 3, 3);
    assertThat(sut.getRgbLine(1)).containsExactly(4, 5, 6);
  }

  @Test
  void rgbLineMatchesTheLineInTheImageNotQuadratic() {
    Image sut = new Image(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 4, 2);
    assertThat(sut.getRgbLine(1)).containsExactly(5, 6, 7, 8);
  }

  @Test
  void rgbColumnMatchesTheColumnInTheImage() {
    Image sut = new Image(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 3, 3);
    assertThat(sut.getRgbColumn(1)).containsExactly(2, 5, 8);
  }

  @Test
  void rgbColumnMatchesTheColumnInTheImageNotQuadratic() {
    Image sut = new Image(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 2, 4);
    assertThat(sut.getRgbColumn(1)).containsExactly(2, 4, 6, 8);
  }

  @Test
  void subImageTest() {
    Section section = new Section(new Position(2, 2), 2, 2);
    Image sut = new Image(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}, 4, 4);
    assertThat(sut.getSubImage(section)).isEqualTo(new Image(new int[]{11, 12, 15, 16}, 2, 2));
  }
}