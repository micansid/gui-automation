package io.github.mschmidae.guiautomation.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class SectionTest {
    @Test
    void checkThrownExceptionIfScreenPositionIsNull() {
        assertThatThrownBy(() -> new Section(null, 0, 0)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkThrownExceptionWidthOrHeightIsNegative() {
        assertThatThrownBy(() -> new Section(new Position(0,0), -1, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Section(new Position(0,0), 0, -1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkThrownExceptionIfScreenPositionsAreNull() {
        assertThatThrownBy(() -> new Section(null, new Position(0, 0))).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Section(new Position(0, 0), null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void screenSectionOfTheSameScreenPositionShouldHaveWidthAndHeightOne() {
        Position position = new Position(1 , 1);
        Section sut = new Section(position, position);
        assertThat(sut.getWidth()).isEqualTo(1);
        assertThat(sut.getHeight()).isEqualTo(1);
        assertThat(sut.getStartPosition()).isEqualTo(position);
        assertThat(sut.getEndPosition()).isEqualTo(position);
    }

    @Test
    void screenSectionWithWidthAndHeightOneShouldHaveEqualScreenPositionsAsBound() {
        Position position = new Position(1 , 1);
        Section sut = new Section(position, 1, 1);
        assertThat(sut.getStartPosition()).isEqualTo(position);
        assertThat(sut.getEndPosition()).isEqualTo(position);
        assertThat(sut.getWidth()).isEqualTo(1);
        assertThat(sut.getHeight()).isEqualTo(1);
    }
}