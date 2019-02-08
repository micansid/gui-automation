package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.algorithm.find.FinderTestData;
import io.github.mschmidae.guiautomation.util.Section;
import io.github.mschmidae.guiautomation.util.image.Image;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class ScreenTest {
    @Test
    void positionOfCommitButton() {
        Screen sut = new Screen(FinderTestData.SCREEN);

        assertThat(sut.positionOf(FinderTestData.BUTTON_COMMIT))
                .isPresent()
                .contains(FinderTestData.BUTTON_COMMIT.getPositions().get(0));
    }

    @Test
    void positionOfCommitButtonInSection() {
        Screen sut = new Screen(FinderTestData.SCREEN);
        Section section = new Section(FinderTestData.BUTTON_COMMIT.getPositions().get(0),
                FinderTestData.BUTTON_COMMIT.getImage().getWidth(),
                FinderTestData.BUTTON_COMMIT.getImage().getHeight());

        assertThat(sut.positionOf(FinderTestData.BUTTON_COMMIT, section))
                .isPresent()
                .contains(FinderTestData.BUTTON_COMMIT.getPositions().get(0));
    }

    @Test
    void positionOfCommitButtonNotInSection() {
        Screen sut = new Screen(FinderTestData.SCREEN);
        Section section = new Section(FinderTestData.BUTTON_COMMIT.getPositions()
                .get(0).move(-1, -1),
                FinderTestData.BUTTON_COMMIT.getImage().getWidth(),
                FinderTestData.BUTTON_COMMIT.getImage().getHeight());

        assertThat(sut.positionOf(FinderTestData.BUTTON_COMMIT, section))
                .isEmpty();
    }

    @Test
    void positionOfListReturnsPositionOfFirstImage() {
        Screen sut = new Screen(FinderTestData.SCREEN);
        List<Supplier<Image>> pattern = Arrays.asList(FinderTestData.BUTTON_CANCEL, FinderTestData.BUTTON_HELP, FinderTestData.BUTTON_COMMIT);

        assertThat(sut.positionOf(pattern))
                .isPresent()
                .contains(FinderTestData.BUTTON_CANCEL.getPositions().get(0));
    }
}