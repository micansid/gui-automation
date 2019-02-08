package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.util.image.Image;

import java.awt.*;
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

        BufferedImage image = null;
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
