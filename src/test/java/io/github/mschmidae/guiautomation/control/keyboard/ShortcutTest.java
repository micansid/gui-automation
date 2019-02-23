package io.github.mschmidae.guiautomation.control.keyboard;

import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

  @Getter(AccessLevel.PRIVATE)
  private static class CountingKeyboardCommandExecutorStub implements KeyboardCommandExecutor {

    private final Map<Key, Integer> standing = new HashMap<>();
    private final Shortcut shortcut;

    private CountingKeyboardCommandExecutorStub(final Shortcut shortcut) {
      this.shortcut = shortcut;
    }

    @Override
    public void press(Key key) {
      if (getStanding().containsKey(key)) {
        getStanding().put(key, getStanding().get(key) + 1);
      } else {
        getStanding().put(key, 1);
      }
    }

    @Override
    public void release(Key key) {
      getStanding().put(key, getStanding().get(key) - 1);
    }

    private void verify(final SoftAssertions softly) {
      softly.assertThat(getStanding().values().stream().mapToInt(i -> i).sum())
          .as("The shortcut " + getShortcut() + " doesn't release all pressed keys!")
          .isEqualTo(0);
    }



  }
}