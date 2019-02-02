package io.github.mschmidae.guiautomation.control.screen;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ScreenSectionTest {
    @Test
    void checkThrownExceptionIfScreenPositionIsNull() {
        assertThatThrownBy(() -> new ScreenSection(null, 0, 0)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkThrownExceptionWidthOrHeightIsNegative() {
        assertThatThrownBy(() -> new ScreenSection(new ScreenPosition(0,0), -1, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ScreenSection(new ScreenPosition(0,0), 0, -1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkThrownExceptionIfScreenPositionsAreNull() {
        assertThatThrownBy(() -> new ScreenSection(null, new ScreenPosition(0, 0))).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ScreenSection(new ScreenPosition(0, 0), null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void screenSectionOfTheSameScreenPositionShouldHaveWidthAndHeightOne() {
        ScreenPosition position = new ScreenPosition(1 , 1);
        ScreenSection sut = new ScreenSection(position, position);
        assertThat(sut.getWidth()).isEqualTo(1);
        assertThat(sut.getHeight()).isEqualTo(1);
        assertThat(sut.getStartPosition()).isEqualTo(position);
        assertThat(sut.getEndPosition()).isEqualTo(position);
    }

    @Test
    void screenSectionWithWidthAndHeightOneShouldHaveEqualScreenPositionsAsBound() {
        ScreenPosition position = new ScreenPosition(1 , 1);
        ScreenSection sut = new ScreenSection(position, 1, 1);
        assertThat(sut.getStartPosition()).isEqualTo(position);
        assertThat(sut.getEndPosition()).isEqualTo(position);
        assertThat(sut.getWidth()).isEqualTo(1);
        assertThat(sut.getHeight()).isEqualTo(1);
    }
}