package io.github.micansid.guiautomation.control.keyboard;

import io.github.micansid.guiautomation.control.awt.AwtKeyboardCommandExecutor;
import io.github.micansid.guiautomation.util.helper.Ensure;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter(AccessLevel.PRIVATE)
public class Keyboard {
  private final KeyboardCommandExecutor executor;
  private final CharacterKeyMapping characterKeyMapping = new CharacterKeyMapping();

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public Keyboard() {
    this(new AwtKeyboardCommandExecutor());
  }

  public Keyboard(KeyboardCommandExecutor executor) {
    Ensure.notNull(executor);
    this.executor = executor;
  }

  public Keyboard press(final Key key) {
    Ensure.notNull(key);
    getLogger().trace("press " + key);
    getExecutor().press(key);
    return this;
  }

  public Keyboard release(final Key key) {
    Ensure.notNull(key);
    getLogger().trace("release " + key);
    getExecutor().release(key);
    return this;
  }

  public Keyboard input(final Key key) {
    Ensure.notNull(key);
    getLogger().trace("input " + key);
    return press(key).release(key);
  }

  public Keyboard type(final String text) {
    Ensure.notNull(text);
    getLogger().debug("type: " + text);
    CharacterKeyMapping mapping = getCharacterKeyMapping();
    text.chars().mapToObj(mapping::map).forEach(this::execute);
    return this;
  }

  public Keyboard execute(final Consumer<Keyboard> shortcut) {
    Ensure.notNull(shortcut);
    getLogger().debug("execute shortcut: " + shortcut);
    shortcut.accept(this);
    return this;
  }
}
