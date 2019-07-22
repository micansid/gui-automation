package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.TestData;
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
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();

    assertThat(sut.positionOf(TestData.BUTTON_COMMIT))
        .isPresent()
        .contains(TestData.BUTTON_COMMIT.getPositions().get(0));
    assertThat(sut.imageAt(TestData.BUTTON_COMMIT,
        TestData.BUTTON_COMMIT.getPositions().get(0))).isTrue();
  }

  @Test
  void positionOfCommitButtonInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Section section = new Section(TestData.BUTTON_COMMIT.getPositions().get(0),
        TestData.BUTTON_COMMIT.getImage().getWidth(),
        TestData.BUTTON_COMMIT.getImage().getHeight());

    assertThat(sut.positionOf(TestData.BUTTON_COMMIT, section))
        .isPresent()
        .contains(TestData.BUTTON_COMMIT.getPositions().get(0));
  }

  @Test
  void positionOfCommitButtonNotInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Section section = new Section(TestData.BUTTON_COMMIT.getPositions()
        .get(0).move(-1, -1),
        TestData.BUTTON_COMMIT.getImage().getWidth(),
        TestData.BUTTON_COMMIT.getImage().getHeight());

    assertThat(sut.positionOf(TestData.BUTTON_COMMIT, section))
        .isEmpty();
  }

  @Test
  void positionOfListReturnsPositionOfFirstImage() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    List<Supplier<Image>> pattern = Arrays.asList(TestData.BUTTON_CANCEL,
        TestData.BUTTON_HELP, TestData.BUTTON_COMMIT);

    assertThat(sut.positionOf(pattern))
        .isPresent()
        .contains(TestData.BUTTON_CANCEL.getPositions().get(0));
  }

  @Test
  void positionOfListReturnsPositionOfFirstImageInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    List<Supplier<Image>> pattern = Arrays.asList(TestData.BUTTON_COMMIT,
        TestData.BUTTON_CANCEL, TestData.BUTTON_HELP);
    Section section = new Section(new Position(500, 0), new Position(770, 826));

    assertThat(sut.positionOf(pattern, section))
        .isPresent()
        .contains(TestData.BUTTON_CANCEL.getPositions().get(0));
  }


  @Test
  void positionsOfUncheckedCheckboxes() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();

    assertThat(sut.positionsOf(TestData.CHECKBOX_UNCHECKED))
        .isEqualTo(TestData.CHECKBOX_UNCHECKED.getPositions());
  }

  @Test
  void positionsOfUncheckedCheckboxesInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Section section = new Section(new Position(474, 0), new Position(770, 826));

    assertThat(sut.positionsOf(TestData.CHECKBOX_UNCHECKED, section))
        .containsExactly(new Position(474, 106), new Position(474, 129),
            new Position(474, 152));
  }

  @Test
  void positionsOfDifferentPatterns() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Set<Supplier<Image>> patternSuppliers = new HashSet<>();
    patternSuppliers.add(TestData.CHECKBOX_CHECKED);
    patternSuppliers.add(TestData.CHECKBOX_UNCHECKED);
    patternSuppliers.add(TestData.BUTTON_COMMIT);

    Map<Image, List<Position>> expected = new HashMap<>();
    expected.put(TestData.BUTTON_COMMIT.getImage(),
        TestData.BUTTON_COMMIT.getPositions());
    expected.put(TestData.CHECKBOX_CHECKED.getImage(),
        TestData.CHECKBOX_CHECKED.getPositions());
    expected.put(TestData.CHECKBOX_UNCHECKED.getImage(),
        TestData.CHECKBOX_UNCHECKED.getPositions());

    Map<Image, List<Position>> result = sut.positionsOf(patternSuppliers);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void positionsOfDifferentPatternsInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Set<Supplier<Image>> patternSuppliers = new HashSet<>();
    patternSuppliers.add(TestData.CHECKBOX_CHECKED);
    patternSuppliers.add(TestData.CHECKBOX_UNCHECKED);
    patternSuppliers.add(TestData.BUTTON_COMMIT);
    patternSuppliers.add(TestData.BUTTON_CANCEL);
    Section section = new Section(new Position(474, 0), new Position(599, 826));

    Map<Image, List<Position>> expected = new HashMap<>();
    expected.put(TestData.BUTTON_COMMIT.getImage(),
        TestData.BUTTON_COMMIT.getPositions());
    expected.put(TestData.BUTTON_CANCEL.getImage(), new ArrayList<>());
    expected.put(TestData.CHECKBOX_CHECKED.getImage(), new ArrayList<>());
    expected.put(TestData.CHECKBOX_UNCHECKED.getImage(),
        Arrays.asList(new Position(474, 106), new Position(474, 129),
            new Position(474, 152)));

    Map<Image, List<Position>> result = sut.positionsOf(patternSuppliers, section);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void clickPositionOfCommitButton() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();

    assertThat(sut.clickPositionOf(TestData.BUTTON_COMMIT))
        .isPresent()
        .contains(TestData.BUTTON_COMMIT.getPositions().get(0).move(51, 12));
  }

  @Test
  void clickPositionOfCommitButtonInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Section section = new Section(TestData.BUTTON_COMMIT.getPositions().get(0),
        TestData.BUTTON_COMMIT.getImage().getWidth(),
        TestData.BUTTON_COMMIT.getImage().getHeight());

    assertThat(sut.clickPositionOf(TestData.BUTTON_COMMIT, section))
        .isPresent()
        .contains(TestData.BUTTON_COMMIT.getPositions().get(0).move(51, 12));
  }

  @Test
  void clickPositionOfCommitButtonNotInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Section section = new Section(TestData.BUTTON_COMMIT.getPositions()
        .get(0).move(-1, -1),
        TestData.BUTTON_COMMIT.getImage().getWidth(),
        TestData.BUTTON_COMMIT.getImage().getHeight());

    assertThat(sut.clickPositionOf(TestData.BUTTON_COMMIT, section))
        .isEmpty();
  }

  @Test
  void clickPositionOfListReturnsPositionOfFirstImage() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    List<Supplier<Image>> pattern = Arrays.asList(TestData.BUTTON_CANCEL,
        TestData.BUTTON_HELP, TestData.BUTTON_COMMIT);

    assertThat(sut.clickPositionOf(pattern))
        .isPresent()
        .contains(TestData.BUTTON_CANCEL.getPositions().get(0).move(36, 12));
  }

  @Test
  void clickPositionOfListReturnsPositionOfFirstImageInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    List<Supplier<Image>> pattern = Arrays.asList(TestData.BUTTON_COMMIT,
        TestData.BUTTON_CANCEL, TestData.BUTTON_HELP);
    Section section = new Section(new Position(500, 0), new Position(770, 826));

    assertThat(sut.clickPositionOf(pattern, section))
        .isPresent()
        .contains(TestData.BUTTON_CANCEL.getPositions().get(0).move(36, 12));
  }

  @Test
  void clickPositionsOfUncheckedCheckboxes() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    assertThat(sut.clickPositionsOf(TestData.CHECKBOX_UNCHECKED))
        .isEqualTo(TestData.CHECKBOX_UNCHECKED.getPositions()
            .stream().map(position -> position.move(7, 7))
            .collect(Collectors.toList()));
  }

  @Test
  void clickPositionsOfUncheckedCheckboxesInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Section section = new Section(new Position(474, 0), new Position(770, 826));

    assertThat(sut.clickPositionsOf(TestData.CHECKBOX_UNCHECKED, section))
        .containsExactly(new Position(474, 106).move(7, 7),
            new Position(474, 129).move(7, 7),
            new Position(474, 152).move(7, 7));
  }

  @Test
  void clickPositionsOfDifferentPatterns() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Set<Supplier<Image>> patternSuppliers = new HashSet<>();
    patternSuppliers.add(TestData.CHECKBOX_CHECKED);
    patternSuppliers.add(TestData.CHECKBOX_UNCHECKED);
    patternSuppliers.add(TestData.BUTTON_COMMIT);

    Map<Image, List<Position>> expected = new HashMap<>();
    expected.put(TestData.BUTTON_COMMIT.getImage(),
        TestData.BUTTON_COMMIT.getPositions()
        .stream().map(position -> position.move(51, 12))
        .collect(Collectors.toList()));
    expected.put(TestData.CHECKBOX_CHECKED.getImage(),
        TestData.CHECKBOX_CHECKED.getPositions()
        .stream().map(position -> position.move(7, 7))
        .collect(Collectors.toList()));
    expected.put(TestData.CHECKBOX_UNCHECKED.getImage(),
        TestData.CHECKBOX_UNCHECKED.getPositions()
        .stream().map(position -> position.move(7, 7))
        .collect(Collectors.toList()));

    Map<Image, List<Position>> result = sut.clickPositionsOf(patternSuppliers);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void clickPositionsOfDifferentPatternsInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Set<Supplier<Image>> patternSuppliers = new HashSet<>();
    patternSuppliers.add(TestData.CHECKBOX_CHECKED);
    patternSuppliers.add(TestData.CHECKBOX_UNCHECKED);
    patternSuppliers.add(TestData.BUTTON_COMMIT);
    patternSuppliers.add(TestData.BUTTON_CANCEL);
    Section section = new Section(new Position(474, 0), new Position(599, 826));

    Map<Image, List<Position>> expected = new HashMap<>();
    expected.put(TestData.BUTTON_COMMIT.getImage(),
        TestData.BUTTON_COMMIT.getPositions()
        .stream().map(position -> position.move(51, 12))
        .collect(Collectors.toList()));
    expected.put(TestData.BUTTON_CANCEL.getImage(), new ArrayList<>());
    expected.put(TestData.CHECKBOX_CHECKED.getImage(), new ArrayList<>());
    expected.put(TestData.CHECKBOX_UNCHECKED.getImage(),
        Arrays.asList(new Position(474, 106).move(7, 7),
        new Position(474, 129).move(7, 7),
        new Position(474, 152).move(7, 7)));

    Map<Image, List<Position>> result = sut.clickPositionsOf(patternSuppliers, section);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void heightAndWidthOfTheScreenImageSupplier() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    assertThat(sut.width()).isEqualTo(771);
    assertThat(sut.height()).isEqualTo(827);
  }

  @Test
  void imagesAtOneOfTheSpecifiedPositionAllPresent() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Map<Image, List<Position>> positions = new HashMap<>();
    positions.put(TestData.BUTTON_COMMIT.getImage(),
        TestData.BUTTON_COMMIT.getPositions());
    positions.put(TestData.CHECKBOX_UNCHECKED.getImage(),
        TestData.CHECKBOX_UNCHECKED.getPositions());
    Map<Image, Boolean> result = sut.imagesAtOnePosition(positions);
    assertThat(result).doesNotContainValue(false);
  }

  @Test
  void imagesAtAllSpecifiedPositionArePresent() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Map<Image, List<Position>> positions = new HashMap<>();
    positions.put(TestData.BUTTON_COMMIT.getImage(),
        TestData.BUTTON_COMMIT.getPositions());
    positions.put(TestData.CHECKBOX_UNCHECKED.getImage(),
        TestData.CHECKBOX_UNCHECKED.getPositions());
    Map<Image, Boolean> result = sut.imagesAtAllPositions(positions);
    assertThat(result).doesNotContainValue(false);
  }

  @Test
  void imagesAtOneOfTheSpecifiedPositionOneOfManyMissing() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Map<Image, List<Position>> positions = new HashMap<>();
    positions.put(TestData.BUTTON_COMMIT.getImage(),
        TestData.BUTTON_COMMIT.getPositions());
    List<Position> uncheckCheckboxes = new ArrayList<>(TestData.CHECKBOX_UNCHECKED.getPositions());
    uncheckCheckboxes.add(new Position(0, 0));
    positions.put(TestData.CHECKBOX_UNCHECKED.getImage(), uncheckCheckboxes);
    Map<Image, Boolean> result = sut.imagesAtOnePosition(positions);
    assertThat(result.get(TestData.BUTTON_COMMIT.getImage())).isTrue();
    assertThat(result.get(TestData.CHECKBOX_UNCHECKED.getImage())).isTrue();
  }

  @Test
  void imagesAtAllSpecifiedPositionOneOfManyMissing() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Map<Image, List<Position>> positions = new HashMap<>();
    positions.put(TestData.BUTTON_COMMIT.getImage(),
        TestData.BUTTON_COMMIT.getPositions());
    List<Position> uncheckCheckboxes = new ArrayList<>(TestData.CHECKBOX_UNCHECKED.getPositions());
    uncheckCheckboxes.add(new Position(0, 0));
    positions.put(TestData.CHECKBOX_UNCHECKED.getImage(), uncheckCheckboxes);
    Map<Image, Boolean> result = sut.imagesAtAllPositions(positions);
    assertThat(result.get(TestData.BUTTON_COMMIT.getImage())).isTrue();
    assertThat(result.get(TestData.CHECKBOX_UNCHECKED.getImage())).isFalse();
  }

  @Test
  void imagesAtOneOfTheSpecifiedPositionOneMissing() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Map<Image, List<Position>> positions = new HashMap<>();
    positions.put(TestData.BUTTON_COMMIT.getImage(),
        Arrays.asList(new Position(0, 0)));
    positions.put(TestData.CHECKBOX_UNCHECKED.getImage(),
        TestData.CHECKBOX_UNCHECKED.getPositions());
    Map<Image, Boolean> result = sut.imagesAtOnePosition(positions);
    assertThat(result.get(TestData.BUTTON_COMMIT.getImage())).isFalse();
    assertThat(result.get(TestData.CHECKBOX_UNCHECKED.getImage())).isTrue();
  }

  @Test
  void imagesAtAllSpecifiedPositionOneMissing() {
    Screen sut = new ScreenBuilder().setScreenSupplier(TestData.SCREEN).build();
    Map<Image, List<Position>> positions = new HashMap<>();
    positions.put(TestData.BUTTON_COMMIT.getImage(),
        Arrays.asList(new Position(0, 0)));
    positions.put(TestData.CHECKBOX_UNCHECKED.getImage(),
        TestData.CHECKBOX_UNCHECKED.getPositions());
    Map<Image, Boolean> result = sut.imagesAtAllPositions(positions);
    assertThat(result.get(TestData.BUTTON_COMMIT.getImage())).isFalse();
    assertThat(result.get(TestData.CHECKBOX_UNCHECKED.getImage())).isTrue();
  }
}