package io.github.mschmidae.guiautomation.algorithm.find;

import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.image.Image;
import io.github.mschmidae.guiautomation.util.image.ImageExporter;
import io.github.mschmidae.guiautomation.util.image.ImageLoader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ImagePositionFinderTest {
    private static Stream<ImagePositionFinder> finderProvider() {
        return Stream.of(new SimpleFinder());
    }

    @ParameterizedTest
    @MethodSource("finderProvider")
    void findPositionOfCommitButton(final ImagePositionFinder finder) {
        Image screen = new ImageLoader().load("intellij_commit_changes.png").orElseThrow(() -> new RuntimeException("Screen image could not be loaded"));
        Image pattern = new ImageLoader().load("intellij_button_commit.png").orElseThrow(() -> new RuntimeException("Pattern image could not be loaded"));

        Position expected = new Position(485, 787);

        assertThat(finder.at(screen, pattern, expected)).isTrue();
        assertThat(finder.find(screen, pattern)).isPresent().contains(expected);
    }

    @ParameterizedTest
    @MethodSource("finderProvider")
    void findPositionOfCancelButton(final ImagePositionFinder finder) {
        Image screen = new ImageLoader().load("intellij_commit_changes.png").orElseThrow(() -> new RuntimeException("Screen image could not be loaded"));
        Image pattern = new ImageLoader().load("intellij_button_cancel.png").orElseThrow(() -> new RuntimeException("Pattern image could not be loaded"));

        Position expected = new Position(599, 787);

        assertThat(finder.at(screen, pattern, expected)).isTrue();
        assertThat(finder.find(screen, pattern)).isPresent().contains(expected);
    }

    @ParameterizedTest
    @MethodSource("finderProvider")
    void findPositionOfHelpButton(final ImagePositionFinder finder) {
        Image screen = new ImageLoader().load("intellij_commit_changes.png").orElseThrow(() -> new RuntimeException("Screen image could not be loaded"));
        Image pattern = new ImageLoader().load("intellij_button_help.png").orElseThrow(() -> new RuntimeException("Pattern image could not be loaded"));

        Position expected = new Position(683, 787);

        assertThat(finder.at(screen, pattern, expected)).isTrue();
        assertThat(finder.find(screen, pattern)).isPresent().contains(expected);
    }

    @ParameterizedTest
    @MethodSource("finderProvider")
    void findAllPositionsOfUncheckedCheckboxes(final ImagePositionFinder finder) {
        Image screen = new ImageLoader().load("intellij_commit_changes.png").orElseThrow(() -> new RuntimeException("Screen image could not be loaded"));
        Image pattern = new ImageLoader().load("intellij_checkbox_unchecked.png").orElseThrow(() -> new RuntimeException("Pattern image could not be loaded"));

        Position[] expected = new Position[] {
                new Position(474, 106),
                new Position(474, 129),
                new Position(474, 152),
                new Position(472, 224),
                new Position(472, 248),
                new Position(472, 272),
                new Position(472, 344),
                new Position(472, 368),
                new Position(472, 392),
                new Position(472, 416),
                new Position(472, 440)
        };

        assertThat(finder.findAll(screen, pattern)).containsExactly(expected);
    }

    @ParameterizedTest
    @MethodSource("finderProvider")
    void findAllPositionsOfCheckedCheckboxes(final ImagePositionFinder finder) {
        Image screen = new ImageLoader().load("intellij_commit_changes.png").orElseThrow(() -> new RuntimeException("Screen image could not be loaded"));
        Image pattern = new ImageLoader().load("intellij_checkbox_checked.png").orElseThrow(() -> new RuntimeException("Pattern image could not be loaded"));

        Position[] expected = new Position[] {
                new Position(472, 296),
                new Position(472, 320),
                new Position(472, 567)
        };

        assertThat(finder.findAll(screen, pattern)).containsExactly(expected);
    }

    @ParameterizedTest
    @MethodSource("finderProvider")
    void findAllPositionsOfButtonFrames(final ImagePositionFinder finder) {
        Image screen = new ImageLoader().load("intellij_commit_changes.png").orElseThrow(() -> new RuntimeException("Screen image could not be loaded"));
        Image pattern = new ImageLoader().load("intellij_button_frame.png").orElseThrow(() -> new RuntimeException("Pattern image could not be loaded"));

        Position[] expected = new Position[] {
                new Position(599, 787),
                new Position(683, 787)
        };

        assertThat(finder.findAll(screen, pattern)).containsExactly(expected);
    }
}