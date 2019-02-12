package io.github.mschmidae.guiautomation.util.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageExporter {
  public boolean export(final BufferedImage image, final String path) {
    boolean result = false;
    try {
      File outputfile = new File(path + ".png");
      ImageIO.write(image, "png", outputfile);
      result = true;
    } catch (IOException e) {
      //result ist false
    }
    return result;
  }

  public boolean export(final Image image, final String path) {
    return export(image.bufferedImage(), path);
  }
}
