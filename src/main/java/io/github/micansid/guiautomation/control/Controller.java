package io.github.micansid.guiautomation.control;

import io.github.micansid.guiautomation.control.clipboard.Clipboard;
import io.github.micansid.guiautomation.control.keyboard.Keyboard;
import io.github.micansid.guiautomation.control.keyboard.Shortcut;
import io.github.micansid.guiautomation.control.mouse.Mouse;
import io.github.micansid.guiautomation.control.screen.Screen;
import io.github.micansid.guiautomation.control.screen.ScreenObserver;
import io.github.micansid.guiautomation.util.Position;
import io.github.micansid.guiautomation.util.helper.Ensure;
import io.github.micansid.guiautomation.util.image.Image;
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


  public Controller(final Clipboard clipboard, final Keyboard keyboard, final Mouse mouse,
                     final Screen screen, final Supplier<Long> clock, final int refreshInterval) {
    Ensure.notNull(clipboard);
    Ensure.notNull(keyboard);
    Ensure.notNull(mouse);
    Ensure.notNull(screen);
    Ensure.notNull(clock);
    Ensure.notNegative(refreshInterval);
    this.clipboard = clipboard;
    this.keyboard = keyboard;
    this.mouse = mouse;
    this.screen = screen;
    this.screenObserver = new ScreenObserver(screen, clock, refreshInterval);
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

  private Optional<Position> clickAtPosition(final Supplier<Optional<Position>> positionSupplier) {
    Optional<Position> clickPosition = positionSupplier.get();
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

  public Optional<Position> clickButton(final Supplier<Image> patternSupplier) {
    return clickAtPosition(() -> screen().clickPositionOf(patternSupplier));
  }

  public Optional<Position> clickButton(final Supplier<Image> patternSupplier, final int timeout) {
    return clickAtPosition(() -> screenObserver().waitUntil(patternSupplier, timeout)
        .map(position -> position.addSubPosition(patternSupplier.get().middle())));
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
