package io.github.mschmidae.guiautomation.algorithm.find;

import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.image.Image;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ImagePositionFinderTest {
  private static Stream<ImagePositionFinder> finderProvider() {
    return Stream.of(
        new SimpleFinder(),
        new SimpleFinderStream(),
        new BadCharacterFinder(),
        new ImagePositionFinderBenchmark(new SimpleFinder(), new BadCharacterFinder()));
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findPositionOfCommitButton(final ImagePositionFinder finder) {
    findFirstPositionOf(finder, FinderTestData.BUTTON_COMMIT);
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findPositionOfCancelButton(final ImagePositionFinder finder) {
    findFirstPositionOf(finder, FinderTestData.BUTTON_CANCEL);
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findPositionOfHelpButton(final ImagePositionFinder finder) {
    findFirstPositionOf(finder, FinderTestData.BUTTON_HELP);
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findFirstPositionsOfUncheckedCheckboxes(final ImagePositionFinder finder) {
    findFirstPositionOf(finder, FinderTestData.CHECKBOX_UNCHECKED);
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findFirstPositionsOfCheckedCheckboxes(final ImagePositionFinder finder) {
    findFirstPositionOf(finder, FinderTestData.CHECKBOX_CHECKED);
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findFirstPositionsOfButtonFramesContainsTransparentParts(final ImagePositionFinder finder) {
    findFirstPositionOf(finder, FinderTestData.BUTTON_FRAME);
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findPositionOfScreenImage(final ImagePositionFinder finder) {
    findFirstPositionOf(finder, FinderTestData.SCREEN);
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findAllPositionOfCommitButton(final ImagePositionFinder finder) {
    findAllPositionOf(finder, FinderTestData.BUTTON_COMMIT);
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findAllPositionOfCancelButton(final ImagePositionFinder finder) {
    findAllPositionOf(finder, FinderTestData.BUTTON_CANCEL);
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findAllPositionOfHelpButton(final ImagePositionFinder finder) {
    findAllPositionOf(finder, FinderTestData.BUTTON_HELP);
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findAllPositionsOfUncheckedCheckboxes(final ImagePositionFinder finder) {
    findAllPositionOf(finder, FinderTestData.CHECKBOX_UNCHECKED);
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findAllPositionsOfCheckedCheckboxes(final ImagePositionFinder finder) {
    findAllPositionOf(finder, FinderTestData.CHECKBOX_CHECKED);
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findAllPositionsOfButtonFramesContainsTransparentParts(final ImagePositionFinder finder) {
    findAllPositionOf(finder, FinderTestData.BUTTON_FRAME);
  }

  @ParameterizedTest
  @MethodSource("finderProvider")
  void findAllPositionsOfAllPattern(final ImagePositionFinder finder) {
    Map<Image, List<Position>> expected = new HashMap<>();
    for (FinderTestData data : FinderTestData.values()) {
      expected.put(data.getImage(), data.getPositions());
    }

    Map<Image, List<Position>> result = finder.findAll(FinderTestData.SCREEN.getImage(),
        expected.keySet());

    assertThat(result).isEqualTo(expected);
  }


  private void findFirstPositionOf(final ImagePositionFinder finder, final FinderTestData pattern) {
    assertThat(finder.at(FinderTestData.SCREEN.getImage(), pattern.getImage(),
        pattern.getPositions().get(0)))
        .isTrue();
    assertThat(finder.find(FinderTestData.SCREEN.getImage(), pattern.getImage()))
        .isPresent()
        .contains(pattern.getPositions().get(0));
  }

  private void findAllPositionOf(final ImagePositionFinder finder, final FinderTestData pattern) {
    assertThat(finder.findAll(FinderTestData.SCREEN.getImage(), pattern.getImage()))
        .isEqualTo(pattern.getPositions());
  }

}