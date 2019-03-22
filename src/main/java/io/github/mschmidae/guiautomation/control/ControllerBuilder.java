package io.github.mschmidae.guiautomation.control;

import io.github.mschmidae.guiautomation.control.clipboard.Clipboard;
import io.github.mschmidae.guiautomation.control.keyboard.Keyboard;
import io.github.mschmidae.guiautomation.control.mouse.Mouse;
import io.github.mschmidae.guiautomation.control.screen.Screen;
import io.github.mschmidae.guiautomation.control.screen.ScreenBuilder;
import io.github.mschmidae.guiautomation.util.helper.Ensure;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class ControllerBuilder {
  private Clipboard clipboard = new Clipboard();
  private Keyboard keyboard = new Keyboard();
  private Mouse mouse = new Mouse();
  private Screen screen = new ScreenBuilder().build();

  public Controller build() {
    return new Controller(getClipboard(), getKeyboard(), getMouse(), getScreen());
  }

  private ControllerBuilder setClipboard(final Clipboard clipboard) {
    Ensure.notNull(clipboard);
    this.clipboard = clipboard;
    return this;
  }

  private ControllerBuilder setKeyboard(final Keyboard keyboard) {
    Ensure.notNull(keyboard);
    this.keyboard = keyboard;
    return this;
  }

  private ControllerBuilder setMouse(final Mouse mouse) {
    Ensure.notNull(mouse);
    this.mouse = mouse;
    return this;
  }

  private ControllerBuilder setScreen(final Screen screen) {
    Ensure.notNull(screen);
    this.screen = screen;
    return this;
  }
}
