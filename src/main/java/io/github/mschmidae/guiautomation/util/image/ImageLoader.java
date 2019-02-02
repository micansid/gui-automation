package io.github.mschmidae.guiautomation.util.image;

import io.github.mschmidae.guiautomation.util.Ensure;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ImageLoader {
    public Optional<Image> load(final String path) {
        Ensure.notBlank(path);


        Optional<Image> result = Optional.empty();

        try {
            BufferedImage bufferedImage = ImageIO.read(new File(ClassLoader.getSystemClassLoader().getResource(path).getFile()));
            result = Optional.of(new Image(bufferedImage));

        } catch (IOException | NullPointerException exception) {
            // return empty optional
        }

        return result;
    }
}
