package io.github.mschmidae.guiautomation.control.keyboard;

import java.util.Map;
import java.util.function.Consumer;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;


class CharacterKeyMappingTest {
  @Test
  void shortcutReleasesAllPressedKeys() {
    CharacterKeyMapping sut = new CharacterKeyMapping();
    SoftAssertions softly = new SoftAssertions();
    for (Character character : sut.getCharacterKeyMapping().keySet()) {
      CountingKeyboardCommandExecutorStub executor =
          new CountingKeyboardCommandExecutorStub(character);
      Keyboard keyboard = new Keyboard(executor);
      keyboard.execute(sut.map(character));
      executor.verify(softly);
    }
    softly.assertAll();
  }
}