package io.github.mschmidae.guiautomation.control.awt;

import io.github.mschmidae.guiautomation.control.awt.AwtKeyMapping;
import io.github.mschmidae.guiautomation.control.keyboard.Key;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThatThrownBy;


class AwtKeyMappingTest {
  @Test
  void mappingForEachKey() {
    AwtKeyMapping sut = new AwtKeyMapping();
    SoftAssertions softly = new SoftAssertions();
    for (Key key : Key.values()) {
      softly.assertThat(sut.map(key))
          .describedAs(key + " not mapped")
          .isNotEqualTo(0);
    }
    softly.assertAll();
  }

  @Test
  void mappingThrowsIllegalArgumentExceptionAtNullParameter() {
    AwtKeyMapping sut = new AwtKeyMapping();
    assertThatThrownBy(() -> sut.map(null)).isInstanceOf(IllegalArgumentException.class);
  }

}