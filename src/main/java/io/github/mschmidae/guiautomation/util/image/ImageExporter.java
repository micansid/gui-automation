package io.github.mschmidae.guiautomation.util.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageExporter {
  /**
   * Export an image to the disk.
   * @param image to export
   * @param path where the image should exported
   * @return result of the export as a boolean
   */
  public boolean export(final BufferedImage image, final String path) {
    boolean result = false;
    try {
      File outputfile = new File(path);
      ImageIO.write(image, "png", outputfile);
      result = true;
    } catch (IOException e) {
      //result ist false
      //ToDo Logging
    }
    return result;
  }

  public boolean export(final Image image, final String path) {
    return export(image.bufferedImage(), path);
  }
}
