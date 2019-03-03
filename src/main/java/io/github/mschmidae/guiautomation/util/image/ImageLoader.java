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
   * @param path of the image to load
   * @return BufferedImage when the image exists, else an empty optional
   */
  public Optional<BufferedImage> loadBufferedImage(final String path) {
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

  public Optional<Image> load(final String path) {
    return loadBufferedImage(path).map(Image::new);
  }
}
