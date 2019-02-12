package io.github.mschmidae.guiautomation.util;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PositionTest {
  @Test
  void twoScreenPositionsWithSameXAndYAreEqual() {
    int x = 0;
    int y = 1;
    Position position1 = new Position(x, y);
    Position position2 = new Position(x, y);

    assertThat(position1).isEqualTo(position2);
    assertThat(position1.getX()).isEqualTo(position2.getX());
    assertThat(position1.getY()).isEqualTo(position2.getY());
  }

  @Test
  void checkThrownExceptionIfPositionIsSmallerThanZero() {
    assertThatThrownBy(() -> new Position(-1, 0))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new Position(0, -1))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void addTwoScreenPositionsOriginalsUntouched() {
    int x = 1;
    int y = 2;
    Position position1 = new Position(x, y);
    Position position2 = new Position(x, y);
    Position position3 = position1.addSubPosition(position2);
    assertThat(position1.getX()).isEqualTo(x);
    assertThat(position1.getY()).isEqualTo(y);
    assertThat(position2.getX()).isEqualTo(x);
    assertThat(position2.getY()).isEqualTo(y);
    assertThat(position3.getX()).isEqualTo(x + x);
    assertThat(position3.getY()).isEqualTo(y + y);
  }

  @Test
  void moveScreenPositionInPositiveDirection() {
    int x = 1;
    int y = 2;
    Position position = new Position(x, y);
    Position moved = position.move(x, y);
    assertThat(moved.getX()).isEqualTo(x + x);
    assertThat(moved.getY()).isEqualTo(y + y);
  }

  @Test
  void moveScreenPositionInNegativeDirection() {
    int x = 1;
    int y = 2;
    Position position = new Position(x, y);
    Position moved = position.move(-x, -y);
    assertThat(moved.getX()).isEqualTo(0);
    assertThat(moved.getY()).isEqualTo(0);
  }

  @Test
  void moveScreenPositionToNegativeCoordinatesShouldFail() {
    int x = 1;
    int y = 2;
    Position position = new Position(x, y);
    assertThatThrownBy(() -> position.move(-2, 0))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> position.move(0, -3))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void compareScreenPositions() {
    int x1 = 1;
    int x2 = 3;
    int y1 = 2;
    int y2 = 4;

    Position position1 = new Position(x1, y1);
    Position position2 = new Position(x2, y1);
    Position position3 = new Position(x1, y2);
    Position position4 = new Position(x2, y2);

    assertThat(position1.compareTo(position1)).isEqualTo(0);
    assertThat(position1.equals(position1)).isTrue();
    assertThat(position1.compareTo(position2)).isNegative();
    assertThat(position1.compareTo(position3)).isNegative();
    assertThat(position1.compareTo(position4)).isNegative();

    assertThat(position2.compareTo(position1)).isPositive();
    assertThat(position2.compareTo(position2)).isEqualTo(0);
    assertThat(position2.equals(position2)).isTrue();
    assertThat(position2.compareTo(position3)).isNegative();
    assertThat(position2.compareTo(position4)).isNegative();

    assertThat(position3.compareTo(position1)).isPositive();
    assertThat(position3.compareTo(position2)).isPositive();
    assertThat(position3.compareTo(position3)).isEqualTo(0);
    assertThat(position3.equals(position3)).isTrue();
    assertThat(position3.compareTo(position4)).isNegative();

    assertThat(position4.compareTo(position1)).isPositive();
    assertThat(position4.compareTo(position2)).isPositive();
    assertThat(position4.compareTo(position3)).isPositive();
    assertThat(position4.compareTo(position4)).isEqualTo(0);
    assertThat(position4.equals(position4)).isTrue();
  }

  @Test
  void compareScreenPositionWithNull() {
    assertThatThrownBy(() -> new Position(0, 0).compareTo(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void scaleUpOriginShouldReturnTheLeftUpperPositionOfTheSection() {
    Position leftUpper = new Position(1, 2);
    Position origin = new Position(0, 0);
    Section sut = new Section(leftUpper, 2, 2);

    assertThat(sut.scaleUpPosition(origin)).isEqualTo(leftUpper);
  }

  @Test
  void scaleUpShouldReturnTheLeftUpperPositionWithAddedToScale() {
    Position leftUpper = new Position(1, 2);
    Position toScale = new Position(3, 4);
    Section sut = new Section(leftUpper, 5, 5);

    assertThat(sut.scaleUpPosition(toScale)).isEqualTo(new Position(4, 6));
  }

  @Test
  void sectionCantScaleOutOfTheSection() {
    Position leftUpper = new Position(1, 2);
    Section sut = new Section(leftUpper, 3, 4);
    assertThat(sut.scaleUpPosition(new Position(2, 0))).isEqualTo(new Position(3, 2));
    assertThat(sut.scaleUpPosition(new Position(0, 3))).isEqualTo(new Position(1, 5));
    assertThatThrownBy(() -> sut.scaleUpPosition(new Position(3, 0)))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> sut.scaleUpPosition(new Position(0, 4)))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void toStringContainsXAndY() {
    Position sut = new Position(1, 2);
    assertThat(sut.toString()).contains("(1|2)");
  }
}