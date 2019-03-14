package io.github.mschmidae.guiautomation.util.image;

import io.github.mschmidae.guiautomation.util.helper.Ensure;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.imageio.ImageIO;

public class ImageLoader {
  /**
   * Load a image from the resources directory.
   * @param path of the image to loadFromResources
   * @return BufferedImage when the image exists, else an empty optional
   */
  public Optional<BufferedImage> loadBufferedImageFromResources(final String path) {
    Ensure.notBlank(path);

    Optional<BufferedImage> result = Optional.empty();

    try {
      InputStream inputStream = getClass().getResourceAsStream("/" + path);
      BufferedImage bufferedImage = ImageIO.read(inputStream);
      result = Optional.of(bufferedImage);

    } catch (IOException | NullPointerException exception) {
      // return empty optional
    }

    return result;
  }

  public Optional<Image> loadFromResources(final String path) {
    return loadBufferedImageFromResources(path).map(Image::new);
  }
}
