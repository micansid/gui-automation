package io.github.mschmidae.guiautomation.algorithm.find;

import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.image.Image;
import io.github.mschmidae.guiautomation.util.image.ImageLoader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ImagePositionFinderTest {
    private static Stream<ImagePositionFinder> finderProvider() {
        return Stream.of(new SimpleFinder());
    }

    @ParameterizedTest
    @MethodSource("finderProvider")
    void findPositionOfUniquePosition(final ImagePositionFinder finder) {
        Image screen = new ImageLoader().load("FuBK_testcard.png").orElseThrow(() -> new RuntimeException("Image could not be loaded"));
        Image pattern = new Image(new int[]{-4259840, -16777025, -4276546, -4276546}, 2, 2);

        Position expected = new Position(1259, 519);

        assertThat(finder.find(screen, pattern)).isPresent().contains(expected);
    }
}