package io.github.mschmidae.guiautomation.control.keyboard;

import io.github.mschmidae.guiautomation.util.helper.Ensure;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public class Keyboard {
  private final KeyboardCommandExecutor executor;
  private final CharacterKeyMapping characterKeyMapping = new CharacterKeyMapping();

  public Keyboard() {
    this(new AwtKeyboardCommandExecutor());
  }

  public Keyboard(KeyboardCommandExecutor executor) {
    Ensure.notNull(executor);
    this.executor = executor;
  }

  public Keyboard press(final Key key) {
    Ensure.notNull(key);
    getExecutor().press(key);
    return this;
  }

  public Keyboard release(final Key key) {
    Ensure.notNull(key);
    getExecutor().release(key);
    return this;
  }

  public Keyboard input(final Key key) {
    Ensure.notNull(key);
    return press(key).release(key);
  }

  public Keyboard type(final String text) {
    Ensure.notNull(text);
    CharacterKeyMapping mapping = getCharacterKeyMapping();
    text.chars().mapToObj(mapping::map).forEach(this::execute);
    return this;
  }

  public Keyboard execute(final Consumer<Keyboard> shortcut) {
    Ensure.notNull(shortcut);
    shortcut.accept(this);
    return this;
  }
}
