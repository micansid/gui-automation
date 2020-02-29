package io.github.micansid.guiautomation.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class SectionTest {
  @Test
  void checkThrownExceptionIfScreenPositionIsNull() {
    assertThatThrownBy(() -> new Section(null, 0, 0))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void checkThrownExceptionWidthOrHeightIsNegative() {
    assertThatThrownBy(() -> new Section(new Position(0, 0), -1, 0))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new Section(new Position(0, 0), 0, -1))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void checkThrownExceptionIfScreenPositionsAreNull() {
    assertThatThrownBy(() -> new Section(null, new Position(0, 0)))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new Section(new Position(0, 0), null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void screenSectionOfTheSameScreenPositionShouldHaveWidthAndHeightOne() {
    Position position = new Position(1, 1);
    Section sut = new Section(position, position);
    assertThat(sut.getWidth()).isEqualTo(1);
    assertThat(sut.getHeight()).isEqualTo(1);
    assertThat(sut.getStartPosition()).isEqualTo(position);
    assertThat(sut.getEndPosition()).isEqualTo(position);
  }

  @Test
  void screenSectionWithWidthAndHeightOneShouldHaveEqualScreenPositionsAsBound() {
    Position position = new Position(1, 1);
    Section sut = new Section(position, 1, 1);
    assertThat(sut.getStartPosition()).isEqualTo(position);
    assertThat(sut.getEndPosition()).isEqualTo(position);
    assertThat(sut.getWidth()).isEqualTo(1);
    assertThat(sut.getHeight()).isEqualTo(1);
  }

  @Test
  void containsCornerPositionsOfSection() {
    Position leftTop = new Position(1, 1);
    Position leftBottom = new Position(1, 3);
    Position rightTop = new Position(3, 1);
    Position rightBottom = new Position(3, 3);

    Section sut = new Section(leftTop, rightBottom);

    assertThat(sut.contains(leftTop)).isTrue();
    assertThat(sut.contains(leftBottom)).isTrue();
    assertThat(sut.contains(rightTop)).isTrue();
    assertThat(sut.contains(rightBottom)).isTrue();
  }

  @Test
  void containsNotOuterNeighboursOfCornerPositions() {
    Position leftTop = new Position(1, 1);
    Position leftBottom = new Position(1, 3);
    Position rightTop = new Position(3, 1);
    Position rightBottom = new Position(3, 3);

    Section sut = new Section(leftTop, rightBottom);

    assertThat(sut.contains(leftTop.move(-1, 0 ))).isFalse();
    assertThat(sut.contains(leftTop.move(0, -1 ))).isFalse();
    assertThat(sut.contains(leftBottom.move(-1, 0))).isFalse();
    assertThat(sut.contains(leftBottom.move(0, 1))).isFalse();

    assertThat(sut.contains(rightTop.move(1, 0))).isFalse();
    assertThat(sut.contains(rightTop.move(0, -1))).isFalse();
    assertThat(sut.contains(rightBottom.move(1, 0))).isFalse();
    assertThat(sut.contains(rightBottom.move(0, 1))).isFalse();
  }

  @Test
  void containsThrowsExceptionAtNullParameter() {
    Section sut = new Section(new Position(1, 1), 1, 1);
    assertThatThrownBy(() -> sut.contains(null)).isInstanceOf(IllegalArgumentException.class);
  }
}