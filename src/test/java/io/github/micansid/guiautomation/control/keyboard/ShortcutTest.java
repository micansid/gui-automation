package io.github.micansid.guiautomation.control.keyboard;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class ShortcutTest {
  @Test
  void shortcutReleasesAllPressedKeys() {
    SoftAssertions softly = new SoftAssertions();
    for (Shortcut shortcut : Shortcut.values()) {
      CountingKeyboardCommandExecutorStub executor =
          new CountingKeyboardCommandExecutorStub(shortcut);
      Keyboard keyboard = new Keyboard(executor);
      keyboard.execute(shortcut);
      executor.verify(softly);
    }
    softly.assertAll();
  }

}