package io.github.micansid.guiautomation.control.screen;

import io.github.micansid.guiautomation.algorithm.find.FinderTestData;
import io.github.micansid.guiautomation.util.Position;
import io.github.micansid.guiautomation.util.Section;
import io.github.micansid.guiautomation.util.image.Image;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ScreenTest {
  @Test
  void positionOfCommitButton() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();

    Assertions.assertThat(sut.positionOf(FinderTestData.BUTTON_COMMIT))
        .isPresent()
        .contains(FinderTestData.BUTTON_COMMIT.getPositions().get(0));
    assertThat(sut.imageAt(FinderTestData.BUTTON_COMMIT,
        FinderTestData.BUTTON_COMMIT.getPositions().get(0))).isTrue();
  }

  @Test
  void positionOfCommitButtonInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Section section = new Section(FinderTestData.BUTTON_COMMIT.getPositions().get(0),
        FinderTestData.BUTTON_COMMIT.getImage().getWidth(),
        FinderTestData.BUTTON_COMMIT.getImage().getHeight());

    Assertions.assertThat(sut.positionOf(FinderTestData.BUTTON_COMMIT, section))
        .isPresent()
        .contains(FinderTestData.BUTTON_COMMIT.getPositions().get(0));
  }

  @Test
  void positionOfCommitButtonNotInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Section section = new Section(FinderTestData.BUTTON_COMMIT.getPositions()
        .get(0).move(-1, -1),
        FinderTestData.BUTTON_COMMIT.getImage().getWidth(),
        FinderTestData.BUTTON_COMMIT.getImage().getHeight());

    Assertions.assertThat(sut.positionOf(FinderTestData.BUTTON_COMMIT, section))
        .isEmpty();
  }

  @Test
  void positionOfListReturnsPositionOfFirstImage() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    List<Supplier<Image>> pattern = Arrays.asList(FinderTestData.BUTTON_CANCEL,
        FinderTestData.BUTTON_HELP, FinderTestData.BUTTON_COMMIT);

    Assertions.assertThat(sut.positionOf(pattern))
        .isPresent()
        .contains(FinderTestData.BUTTON_CANCEL.getPositions().get(0));
  }

  @Test
  void positionOfListReturnsPositionOfFirstImageInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    List<Supplier<Image>> pattern = Arrays.asList(FinderTestData.BUTTON_COMMIT,
        FinderTestData.BUTTON_CANCEL, FinderTestData.BUTTON_HELP);
    Section section = new Section(new Position(500, 0), new Position(770, 826));

    Assertions.assertThat(sut.positionOf(pattern, section))
        .isPresent()
        .contains(FinderTestData.BUTTON_CANCEL.getPositions().get(0));
  }


  @Test
  void positionsOfUncheckedCheckboxes() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();

    Assertions.assertThat(sut.positionsOf(FinderTestData.CHECKBOX_UNCHECKED))
        .isEqualTo(FinderTestData.CHECKBOX_UNCHECKED.getPositions());
  }

  @Test
  void positionsOfUncheckedCheckboxesInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Section section = new Section(new Position(474, 0), new Position(770, 826));

    Assertions.assertThat(sut.positionsOf(FinderTestData.CHECKBOX_UNCHECKED, section))
        .containsExactly(new Position(474, 106), new Position(474, 129),
            new Position(474, 152));
  }

  @Test
  void positionsOfDifferentPatterns() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Set<Supplier<Image>> patternSuppliers = new HashSet<>();
    patternSuppliers.add(FinderTestData.CHECKBOX_CHECKED);
    patternSuppliers.add(FinderTestData.CHECKBOX_UNCHECKED);
    patternSuppliers.add(FinderTestData.BUTTON_COMMIT);

    Map<Image, List<Position>> expected = new HashMap<>();
    expected.put(FinderTestData.BUTTON_COMMIT.getImage(),
        FinderTestData.BUTTON_COMMIT.getPositions());
    expected.put(FinderTestData.CHECKBOX_CHECKED.getImage(),
        FinderTestData.CHECKBOX_CHECKED.getPositions());
    expected.put(FinderTestData.CHECKBOX_UNCHECKED.getImage(),
        FinderTestData.CHECKBOX_UNCHECKED.getPositions());

    Map<Image, List<Position>> result = sut.positionsOf(patternSuppliers);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void positionsOfDifferentPatternsInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Set<Supplier<Image>> patternSuppliers = new HashSet<>();
    patternSuppliers.add(FinderTestData.CHECKBOX_CHECKED);
    patternSuppliers.add(FinderTestData.CHECKBOX_UNCHECKED);
    patternSuppliers.add(FinderTestData.BUTTON_COMMIT);
    patternSuppliers.add(FinderTestData.BUTTON_CANCEL);
    Section section = new Section(new Position(474, 0), new Position(599, 826));

    Map<Image, List<Position>> expected = new HashMap<>();
    expected.put(FinderTestData.BUTTON_COMMIT.getImage(),
        FinderTestData.BUTTON_COMMIT.getPositions());
    expected.put(FinderTestData.BUTTON_CANCEL.getImage(), new ArrayList<>());
    expected.put(FinderTestData.CHECKBOX_CHECKED.getImage(), new ArrayList<>());
    expected.put(FinderTestData.CHECKBOX_UNCHECKED.getImage(),
        Arrays.asList(new Position(474, 106), new Position(474, 129),
            new Position(474, 152)));

    Map<Image, List<Position>> result = sut.positionsOf(patternSuppliers, section);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void clickPositionOfCommitButton() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();

    Assertions.assertThat(sut.clickPositionOf(FinderTestData.BUTTON_COMMIT))
        .isPresent()
        .contains(FinderTestData.BUTTON_COMMIT.getPositions().get(0).move(51, 12));
  }

  @Test
  void clickPositionOfCommitButtonInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Section section = new Section(FinderTestData.BUTTON_COMMIT.getPositions().get(0),
        FinderTestData.BUTTON_COMMIT.getImage().getWidth(),
        FinderTestData.BUTTON_COMMIT.getImage().getHeight());

    Assertions.assertThat(sut.clickPositionOf(FinderTestData.BUTTON_COMMIT, section))
        .isPresent()
        .contains(FinderTestData.BUTTON_COMMIT.getPositions().get(0).move(51, 12));
  }

  @Test
  void clickPositionOfCommitButtonNotInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Section section = new Section(FinderTestData.BUTTON_COMMIT.getPositions()
        .get(0).move(-1, -1),
        FinderTestData.BUTTON_COMMIT.getImage().getWidth(),
        FinderTestData.BUTTON_COMMIT.getImage().getHeight());

    Assertions.assertThat(sut.clickPositionOf(FinderTestData.BUTTON_COMMIT, section))
        .isEmpty();
  }

  @Test
  void clickPositionOfListReturnsPositionOfFirstImage() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    List<Supplier<Image>> pattern = Arrays.asList(FinderTestData.BUTTON_CANCEL,
        FinderTestData.BUTTON_HELP, FinderTestData.BUTTON_COMMIT);

    Assertions.assertThat(sut.clickPositionOf(pattern))
        .isPresent()
        .contains(FinderTestData.BUTTON_CANCEL.getPositions().get(0).move(36, 12));
  }

  @Test
  void clickPositionOfListReturnsPositionOfFirstImageInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    List<Supplier<Image>> pattern = Arrays.asList(FinderTestData.BUTTON_COMMIT,
        FinderTestData.BUTTON_CANCEL, FinderTestData.BUTTON_HELP);
    Section section = new Section(new Position(500, 0), new Position(770, 826));

    Assertions.assertThat(sut.clickPositionOf(pattern, section))
        .isPresent()
        .contains(FinderTestData.BUTTON_CANCEL.getPositions().get(0).move(36, 12));
  }

  @Test
  void clickPositionsOfUncheckedCheckboxes() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Assertions.assertThat(sut.clickPositionsOf(FinderTestData.CHECKBOX_UNCHECKED))
        .isEqualTo(FinderTestData.CHECKBOX_UNCHECKED.getPositions()
            .stream().map(position -> position.move(7, 7))
            .collect(Collectors.toList()));
  }

  @Test
  void clickPositionsOfUncheckedCheckboxesInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Section section = new Section(new Position(474, 0), new Position(770, 826));

    Assertions.assertThat(sut.clickPositionsOf(FinderTestData.CHECKBOX_UNCHECKED, section))
        .containsExactly(new Position(474, 106).move(7, 7),
            new Position(474, 129).move(7, 7),
            new Position(474, 152).move(7, 7));
  }

  @Test
  void clickPositionsOfDifferentPatterns() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Set<Supplier<Image>> patternSuppliers = new HashSet<>();
    patternSuppliers.add(FinderTestData.CHECKBOX_CHECKED);
    patternSuppliers.add(FinderTestData.CHECKBOX_UNCHECKED);
    patternSuppliers.add(FinderTestData.BUTTON_COMMIT);

    Map<Image, List<Position>> expected = new HashMap<>();
    expected.put(FinderTestData.BUTTON_COMMIT.getImage(),
        FinderTestData.BUTTON_COMMIT.getPositions()
        .stream().map(position -> position.move(51, 12))
        .collect(Collectors.toList()));
    expected.put(FinderTestData.CHECKBOX_CHECKED.getImage(),
        FinderTestData.CHECKBOX_CHECKED.getPositions()
        .stream().map(position -> position.move(7, 7))
        .collect(Collectors.toList()));
    expected.put(FinderTestData.CHECKBOX_UNCHECKED.getImage(),
        FinderTestData.CHECKBOX_UNCHECKED.getPositions()
        .stream().map(position -> position.move(7, 7))
        .collect(Collectors.toList()));

    Map<Image, List<Position>> result = sut.clickPositionsOf(patternSuppliers);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void clickPositionsOfDifferentPatternsInSection() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Set<Supplier<Image>> patternSuppliers = new HashSet<>();
    patternSuppliers.add(FinderTestData.CHECKBOX_CHECKED);
    patternSuppliers.add(FinderTestData.CHECKBOX_UNCHECKED);
    patternSuppliers.add(FinderTestData.BUTTON_COMMIT);
    patternSuppliers.add(FinderTestData.BUTTON_CANCEL);
    Section section = new Section(new Position(474, 0), new Position(599, 826));

    Map<Image, List<Position>> expected = new HashMap<>();
    expected.put(FinderTestData.BUTTON_COMMIT.getImage(),
        FinderTestData.BUTTON_COMMIT.getPositions()
        .stream().map(position -> position.move(51, 12))
        .collect(Collectors.toList()));
    expected.put(FinderTestData.BUTTON_CANCEL.getImage(), new ArrayList<>());
    expected.put(FinderTestData.CHECKBOX_CHECKED.getImage(), new ArrayList<>());
    expected.put(FinderTestData.CHECKBOX_UNCHECKED.getImage(),
        Arrays.asList(new Position(474, 106).move(7, 7),
        new Position(474, 129).move(7, 7),
        new Position(474, 152).move(7, 7)));

    Map<Image, List<Position>> result = sut.clickPositionsOf(patternSuppliers, section);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void heightAndWidthOfTheScreenImageSupplier() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    assertThat(sut.width()).isEqualTo(771);
    assertThat(sut.height()).isEqualTo(827);
  }

  @Test
  void imagesAtOneOfTheSpecifiedPositionAllPresent() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Map<Image, List<Position>> positions = new HashMap<>();
    positions.put(FinderTestData.BUTTON_COMMIT.getImage(),
        FinderTestData.BUTTON_COMMIT.getPositions());
    positions.put(FinderTestData.CHECKBOX_UNCHECKED.getImage(),
        FinderTestData.CHECKBOX_UNCHECKED.getPositions());
    Map<Image, Boolean> result = sut.imagesAtOnePosition(positions);
    assertThat(result).doesNotContainValue(false);
  }

  @Test
  void imagesAtAllSpecifiedPositionArePresent() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Map<Image, List<Position>> positions = new HashMap<>();
    positions.put(FinderTestData.BUTTON_COMMIT.getImage(),
        FinderTestData.BUTTON_COMMIT.getPositions());
    positions.put(FinderTestData.CHECKBOX_UNCHECKED.getImage(),
        FinderTestData.CHECKBOX_UNCHECKED.getPositions());
    Map<Image, Boolean> result = sut.imagesAtAllPositions(positions);
    assertThat(result).doesNotContainValue(false);
  }

  @Test
  void imagesAtOneOfTheSpecifiedPositionOneOfManyMissing() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Map<Image, List<Position>> positions = new HashMap<>();
    positions.put(FinderTestData.BUTTON_COMMIT.getImage(),
        FinderTestData.BUTTON_COMMIT.getPositions());
    List<Position> uncheckCheckboxes = new ArrayList<>(FinderTestData.CHECKBOX_UNCHECKED.getPositions());
    uncheckCheckboxes.add(new Position(0, 0));
    positions.put(FinderTestData.CHECKBOX_UNCHECKED.getImage(), uncheckCheckboxes);
    Map<Image, Boolean> result = sut.imagesAtOnePosition(positions);
    assertThat(result.get(FinderTestData.BUTTON_COMMIT.getImage())).isTrue();
    assertThat(result.get(FinderTestData.CHECKBOX_UNCHECKED.getImage())).isTrue();
  }

  @Test
  void imagesAtAllSpecifiedPositionOneOfManyMissing() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Map<Image, List<Position>> positions = new HashMap<>();
    positions.put(FinderTestData.BUTTON_COMMIT.getImage(),
        FinderTestData.BUTTON_COMMIT.getPositions());
    List<Position> uncheckCheckboxes = new ArrayList<>(FinderTestData.CHECKBOX_UNCHECKED.getPositions());
    uncheckCheckboxes.add(new Position(0, 0));
    positions.put(FinderTestData.CHECKBOX_UNCHECKED.getImage(), uncheckCheckboxes);
    Map<Image, Boolean> result = sut.imagesAtAllPositions(positions);
    assertThat(result.get(FinderTestData.BUTTON_COMMIT.getImage())).isTrue();
    assertThat(result.get(FinderTestData.CHECKBOX_UNCHECKED.getImage())).isFalse();
  }

  @Test
  void imagesAtOneOfTheSpecifiedPositionOneMissing() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Map<Image, List<Position>> positions = new HashMap<>();
    positions.put(FinderTestData.BUTTON_COMMIT.getImage(),
        Arrays.asList(new Position(0, 0)));
    positions.put(FinderTestData.CHECKBOX_UNCHECKED.getImage(),
        FinderTestData.CHECKBOX_UNCHECKED.getPositions());
    Map<Image, Boolean> result = sut.imagesAtOnePosition(positions);
    assertThat(result.get(FinderTestData.BUTTON_COMMIT.getImage())).isFalse();
    assertThat(result.get(FinderTestData.CHECKBOX_UNCHECKED.getImage())).isTrue();
  }

  @Test
  void imagesAtAllSpecifiedPositionOneMissing() {
    Screen sut = new ScreenBuilder().setScreenSupplier(FinderTestData.SCREEN).build();
    Map<Image, List<Position>> positions = new HashMap<>();
    positions.put(FinderTestData.BUTTON_COMMIT.getImage(),
        Arrays.asList(new Position(0, 0)));
    positions.put(FinderTestData.CHECKBOX_UNCHECKED.getImage(),
        FinderTestData.CHECKBOX_UNCHECKED.getPositions());
    Map<Image, Boolean> result = sut.imagesAtAllPositions(positions);
    assertThat(result.get(FinderTestData.BUTTON_COMMIT.getImage())).isFalse();
    assertThat(result.get(FinderTestData.CHECKBOX_UNCHECKED.getImage())).isTrue();
  }
}