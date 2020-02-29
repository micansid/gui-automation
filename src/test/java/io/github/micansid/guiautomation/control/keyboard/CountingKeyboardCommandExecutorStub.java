package io.github.micansid.guiautomation.control.keyboard;

import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import org.assertj.core.api.SoftAssertions;

@Getter(AccessLevel.PRIVATE)
public class CountingKeyboardCommandExecutorStub implements KeyboardCommandExecutor {
  private final Map<Key, Integer> standing = new HashMap<>();
  private final Object identifier;

  CountingKeyboardCommandExecutorStub(final Object identifier) {
    this.identifier = identifier;
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

  void verify(final SoftAssertions softly) {
    softly.assertThat(getStanding().values().stream().mapToInt(i -> i).sum())
        .as("The shortcut " + getIdentifier() + " doesn't release all pressed keys!")
        .isEqualTo(0);
  }
}
