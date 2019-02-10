package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.algorithm.find.FinderTestData;
import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.Section;
import io.github.mschmidae.guiautomation.util.image.Image;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ScreenTest {
    @Test
    void positionOfCommitButton() {
        Screen sut = new Screen(FinderTestData.SCREEN);

        assertThat(sut.positionOf(FinderTestData.BUTTON_COMMIT))
                .isPresent()
                .contains(FinderTestData.BUTTON_COMMIT.getPositions().get(0));
        assertThat(sut.imageAt(FinderTestData.BUTTON_COMMIT,
                FinderTestData.BUTTON_COMMIT.getPositions().get(0))).isTrue();
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

    @Test
    void positionsOfUncheckedCheckboxes() {
        Screen sut = new Screen(FinderTestData.SCREEN);

        assertThat(sut.positionsOf(FinderTestData.CHECKBOX_UNCHECKED))
                .isEqualTo(FinderTestData.CHECKBOX_UNCHECKED.getPositions());
    }

    @Test
    void positionsOfDifferentPatterns() {
        Screen sut = new Screen(FinderTestData.SCREEN);
        Set<Supplier<Image>> patternSuppliers = new HashSet<>();
        patternSuppliers.add(FinderTestData.CHECKBOX_CHECKED);
        patternSuppliers.add(FinderTestData.CHECKBOX_UNCHECKED);
        patternSuppliers.add(FinderTestData.BUTTON_COMMIT);

        Map<Image, List<Position>> expected = new HashMap<>();
        expected.put(FinderTestData.BUTTON_COMMIT.getImage(), FinderTestData.BUTTON_COMMIT.getPositions());
        expected.put(FinderTestData.CHECKBOX_CHECKED.getImage(), FinderTestData.CHECKBOX_CHECKED.getPositions());
        expected.put(FinderTestData.CHECKBOX_UNCHECKED.getImage(), FinderTestData.CHECKBOX_UNCHECKED.getPositions());

        Map<Image, List<Position>> result = sut.positionsOf(patternSuppliers);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void clickPositionOfCommitButton() {
        Screen sut = new Screen(FinderTestData.SCREEN);

        assertThat(sut.clickPositionOf(FinderTestData.BUTTON_COMMIT))
                .isPresent()
                .contains(FinderTestData.BUTTON_COMMIT.getPositions().get(0).move(51, 12));
    }

    @Test
    void clickPositionOfCommitButtonInSection() {
        Screen sut = new Screen(FinderTestData.SCREEN);
        Section section = new Section(FinderTestData.BUTTON_COMMIT.getPositions().get(0),
                FinderTestData.BUTTON_COMMIT.getImage().getWidth(),
                FinderTestData.BUTTON_COMMIT.getImage().getHeight());

        assertThat(sut.clickPositionOf(FinderTestData.BUTTON_COMMIT, section))
                .isPresent()
                .contains(FinderTestData.BUTTON_COMMIT.getPositions().get(0).move(51, 12));
    }

    @Test
    void clickPositionOfCommitButtonNotInSection() {
        Screen sut = new Screen(FinderTestData.SCREEN);
        Section section = new Section(FinderTestData.BUTTON_COMMIT.getPositions()
                .get(0).move(-1, -1),
                FinderTestData.BUTTON_COMMIT.getImage().getWidth(),
                FinderTestData.BUTTON_COMMIT.getImage().getHeight());

        assertThat(sut.clickPositionOf(FinderTestData.BUTTON_COMMIT, section))
                .isEmpty();
    }

    @Test
    void clickPositionOfListReturnsPositionOfFirstImage() {
        Screen sut = new Screen(FinderTestData.SCREEN);
        List<Supplier<Image>> pattern = Arrays.asList(FinderTestData.BUTTON_CANCEL, FinderTestData.BUTTON_HELP, FinderTestData.BUTTON_COMMIT);

        assertThat(sut.clickPositionOf(pattern))
                .isPresent()
                .contains(FinderTestData.BUTTON_CANCEL.getPositions().get(0).move(36, 12));
    }

    @Test
    void clickPositionsOfUncheckedCheckboxes() {
        Screen sut = new Screen(FinderTestData.SCREEN);
        assertThat(sut.clickPositionsOf(FinderTestData.CHECKBOX_UNCHECKED))
                .isEqualTo(FinderTestData.CHECKBOX_UNCHECKED.getPositions()
                        .stream().map(position -> position.move(7, 7))
                        .collect(Collectors.toList()));
    }

    @Test
    void clickPositionsOfDifferentPatterns() {
        Screen sut = new Screen(FinderTestData.SCREEN);
        Set<Supplier<Image>> patternSuppliers = new HashSet<>();
        patternSuppliers.add(FinderTestData.CHECKBOX_CHECKED);
        patternSuppliers.add(FinderTestData.CHECKBOX_UNCHECKED);
        patternSuppliers.add(FinderTestData.BUTTON_COMMIT);

        Map<Image, List<Position>> expected = new HashMap<>();
        expected.put(FinderTestData.BUTTON_COMMIT.getImage(), FinderTestData.BUTTON_COMMIT.getPositions()
                .stream().map(position -> position.move(51, 12))
                .collect(Collectors.toList()));
        expected.put(FinderTestData.CHECKBOX_CHECKED.getImage(), FinderTestData.CHECKBOX_CHECKED.getPositions()
                .stream().map(position -> position.move(7, 7))
                .collect(Collectors.toList()));
        expected.put(FinderTestData.CHECKBOX_UNCHECKED.getImage(), FinderTestData.CHECKBOX_UNCHECKED.getPositions()
                .stream().map(position -> position.move(7, 7))
                .collect(Collectors.toList()));

        Map<Image, List<Position>> result = sut.clickPositionsOf(patternSuppliers);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void heightAndWidthOfTheScreenImageSupplier() {
        Screen sut = new Screen(FinderTestData.SCREEN);
        assertThat(sut.getWidth()).isEqualTo(771);
        assertThat(sut.getHeight()).isEqualTo(827);
    }
}