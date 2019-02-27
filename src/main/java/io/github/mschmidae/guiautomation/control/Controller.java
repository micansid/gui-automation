package io.github.mschmidae.guiautomation.control;

import io.github.mschmidae.guiautomation.control.clipboard.Clipboard;
import io.github.mschmidae.guiautomation.control.keyboard.Keyboard;
import io.github.mschmidae.guiautomation.control.keyboard.Shortcut;
import io.github.mschmidae.guiautomation.control.mouse.Mouse;
import io.github.mschmidae.guiautomation.control.screen.Screen;
import io.github.mschmidae.guiautomation.control.screen.ScreenObserver;
import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.helper.Ensure;
import io.github.mschmidae.guiautomation.util.image.Image;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public class Controller {
  private final Clipboard clipboard;
  private final Keyboard keyboard;
  private final Mouse mouse;
  private final Screen screen;
  private final ScreenObserver screenObserver;

  public Controller() {
    this(new Clipboard(), new Keyboard(), new Mouse(), new Screen());
  }

  public Controller(final Clipboard clipboard, final Keyboard keyboard, final Mouse mouse,
                     final Screen screen) {
    Ensure.notNull(clipboard);
    Ensure.notNull(keyboard);
    Ensure.notNull(mouse);
    Ensure.notNull(screen);
    this.clipboard = clipboard;
    this.keyboard = keyboard;
    this.mouse = mouse;
    this.screen = screen;
    this.screenObserver = new ScreenObserver(screen);
  }

  public Controller pasteText(final String text) {
    clipboard().set(text);
    keyboard().execute(Shortcut.PASTE);
    return this;
  }

  public Optional<String> copyText() {
    keyboard().execute(Shortcut.COPY);
    return clipboard().get();
  }

  public Optional<Position> clickButton(final Supplier<Image> patternSupplier) {
    Optional<Position> clickPosition = screen().clickPositionOf(patternSupplier);
    if (clickPosition.isPresent()) {
      mouse().move(clickPosition.get());
      if (clickPosition.get().equals(mouse().currentPosition())) {
        mouse.leftClick();
      } else {
        clickPosition = Optional.empty();
      }
    }
    return clickPosition;
  }

  public Clipboard clipboard() {
    return getClipboard();
  }

  public Keyboard keyboard() {
    return getKeyboard();
  }

  public Mouse mouse() {
    return getMouse();
  }

  public Screen screen() {
    return getScreen();
  }

  public ScreenObserver screenObserver() {
    return getScreenObserver();
  }
}
