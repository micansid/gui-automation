package io.github.micansid.guiautomation.util.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.AccessLevel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter(AccessLevel.PRIVATE)
public class ImageExporter {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * Export an image to the disk.
   * @param image to export
   * @param path where the image should exported
   * @return result of the export as a boolean
   */
  public boolean export(final BufferedImage image, final String path) {
    boolean result = false;
    try {
      File outputFile = new File(path);
      ImageIO.write(image, "png", outputFile);
      result = true;
    } catch (IOException exception) {
      getLogger().error(exception.getMessage());
    }
    return result;
  }

  public boolean export(final Image image, final String path) {
    return export(image.bufferedImage(), path);
  }
}
