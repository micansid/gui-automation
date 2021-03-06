package io.github.micansid.guiautomation.control.awt;

import io.github.micansid.guiautomation.util.image.Image;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Supplier;

public class AwtScreenshotSupplier implements Supplier<Image> {
  @Override
  public Image get() {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices())
        .map(device -> device.getDefaultConfiguration().getBounds())
        .map(bounds -> bounds.x + bounds.width)
        .max(Comparator.comparingInt(i -> i)).orElse(0);
    int height = Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices())
        .map(device -> device.getDefaultConfiguration().getBounds())
        .map(bounds -> bounds.y + bounds.height)
        .max(Comparator.comparingInt(i -> i)).orElse(0);

    BufferedImage image;
    try {
      Robot robot = new Robot();
      Rectangle rectangle = new Rectangle(0, 0, width, height);
      image = robot.createScreenCapture(rectangle);
    } catch (AWTException e) {
      throw new RuntimeException(e);
    }

    return new Image(image);
  }
}
