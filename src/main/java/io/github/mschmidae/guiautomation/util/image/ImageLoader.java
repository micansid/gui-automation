package io.github.mschmidae.guiautomation.util.image;

import io.github.mschmidae.guiautomation.util.helper.Ensure;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    return load(getClass().getResourceAsStream("/" + path));
  }

  public Optional<Image> loadFromResources(final String path) {
    return loadBufferedImageFromResources(path).map(Image::new);
  }

  public Optional<BufferedImage> loadBufferedImage(final String path) {
    Ensure.notBlank(path);

    Optional<BufferedImage> result = Optional.empty();

    try {
      result = load(new BufferedInputStream(new FileInputStream(path)));
    } catch (FileNotFoundException e) {
      // return empty optional
      //ToDo Logging
    }

    return result;
  }

  public Optional<Image> load(final String path) {
    return loadBufferedImage(path).map(Image::new);
  }

  private Optional<BufferedImage> load(final InputStream inputStream) {
    Optional<BufferedImage> result = Optional.empty();
    try {
      BufferedImage bufferedImage = ImageIO.read(inputStream);
      result = Optional.of(bufferedImage);
    } catch (IOException e) {
      // return empty optional
      //ToDo Logging
    }
    return result;
  }
}
