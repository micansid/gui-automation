package io.github.micansid.guiautomation.control.keyboard;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;


class CharacterKeyMappingTest {
  @Test
  void characterKeyMappingReleasesAllPressedKeys() {
    CharacterKeyMapping sut = new CharacterKeyMapping();
    SoftAssertions softly = new SoftAssertions();
    for (Integer character : sut.getCharacterKeyMapping().keySet()) {
      CountingKeyboardCommandExecutorStub executor =
          new CountingKeyboardCommandExecutorStub(character);
      Keyboard keyboard = new Keyboard(executor);
      keyboard.execute(sut.map(character));
      executor.verify(softly);
    }
    softly.assertAll();
  }
}