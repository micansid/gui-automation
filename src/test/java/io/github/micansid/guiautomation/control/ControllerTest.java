package io.github.micansid.guiautomation.control;

import io.github.micansid.guiautomation.control.clipboard.Clipboard;
import io.github.micansid.guiautomation.control.keyboard.Key;
import io.github.micansid.guiautomation.control.keyboard.Keyboard;
import io.github.micansid.guiautomation.control.keyboard.KeyboardCommandExecutor;
import io.github.micansid.guiautomation.control.mouse.Mouse;
import io.github.micansid.guiautomation.control.mouse.MouseButton;
import io.github.micansid.guiautomation.control.mouse.MouseCommandExecutor;
import io.github.micansid.guiautomation.control.screen.Screen;
import io.github.micansid.guiautomation.util.Position;
import io.github.micansid.guiautomation.util.image.Image;

import java.util.Optional;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class ControllerTest {
  @Test
  void pasteTextFromClipboard() {
    KeyboardCommandExecutor executor = mock(KeyboardCommandExecutor.class);
    Clipboard clipboard = mock(Clipboard.class);

    Controller sut = new ControllerBuilder()
        .setClipboard(clipboard)
        .setKeyboard(new Keyboard(executor))
        .build();

    sut.pasteText("test");
    verify(clipboard).set("test");
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).press(Key.CONTROL);
    inOrder.verify(executor).press(Key.V);
    inOrder.verify(executor).release(Key.V);
    inOrder.verify(executor).release(Key.CONTROL);
    verifyNoMoreInteractions(executor, clipboard);
  }

  @Test
  void copyTextToClipboard() {
    KeyboardCommandExecutor executor = mock(KeyboardCommandExecutor.class);
    Clipboard clipboard = mock(Clipboard.class);

    Controller sut = new ControllerBuilder()
        .setClipboard(clipboard)
        .setKeyboard(new Keyboard(executor))
        .build();

    when(clipboard.get()).thenReturn(Optional.of("test"));

    assertThat(sut.copyText()).isPresent().contains("test");

    verify(clipboard, times(1)).get();
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).press(Key.CONTROL);
    inOrder.verify(executor).press(Key.C);
    inOrder.verify(executor).release(Key.C);
    inOrder.verify(executor).release(Key.CONTROL);
    verifyNoMoreInteractions(executor, clipboard);
  }

  @Test
  void clickExistingButton() {
    Position clickPosition = new Position(2, 3);
    MouseCommandExecutor executor = mock(MouseCommandExecutor.class);
    Mouse mouse = new Mouse(executor, () -> clickPosition);
    Screen screen = mock(Screen.class);

    Controller sut = new ControllerBuilder()
        .setMouse(mouse)
        .setScreen(screen)
        .build();

    when(screen.clickPositionOf(any(Supplier.class))).thenReturn(Optional.of(clickPosition));

    Optional<Position> result = sut.clickButton(() -> new Image(new int[]{0}, 1, 1));

    assertThat(result).isPresent().contains(clickPosition);
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).move(clickPosition.getX(), clickPosition.getY());
    inOrder.verify(executor).press(MouseButton.LEFT);
    inOrder.verify(executor).release(MouseButton.LEFT);
    verifyNoMoreInteractions(executor);
  }

  @Test
  void clickButtonMoveFailed() {
    Position clickPosition = new Position(2, 3);
    MouseCommandExecutor executor = mock(MouseCommandExecutor.class);
    Mouse mouse = new Mouse(executor, () -> new Position(3, 4));
    Screen screen = mock(Screen.class);

    Controller sut = new ControllerBuilder()
        .setMouse(mouse)
        .setScreen(screen)
        .build();

    when(screen.clickPositionOf(any(Supplier.class))).thenReturn(Optional.of(clickPosition));

    Optional<Position> result = sut.clickButton(() -> new Image(new int[]{0}, 1, 1));

    assertThat(result).isEmpty();
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).move(clickPosition.getX(), clickPosition.getY());
    verifyNoMoreInteractions(executor);
  }

  @Test
  void clickButtonPositionDetectionFailed() {
    MouseCommandExecutor executor = mock(MouseCommandExecutor.class);
    Mouse mouse = new Mouse(executor, () -> new Position(0, 0));
    Screen screen = mock(Screen.class);

    Controller sut = new ControllerBuilder()
        .setMouse(mouse)
        .setScreen(screen)
        .build();

    Optional<Position> result = sut.clickButton(() -> new Image(new int[]{0}, 1, 1));

    assertThat(result).isEmpty();
    verifyNoMoreInteractions(executor);
  }

  @Test
  void clickButtonPositionOccursAtThirdCall() {
    Position clickPosition = new Position(2, 3);
    MouseCommandExecutor executor = mock(MouseCommandExecutor.class);
    Mouse mouse = new Mouse(executor, () -> clickPosition);
    Screen screen = mock(Screen.class);
    Supplier<Long> clock = mock(Supplier.class);
    when(clock.get()).thenReturn(0L, 1L, 2L, 3L, 4L, 5L);

    Controller sut = new ControllerBuilder()
        .setMouse(mouse)
        .setScreen(screen)
        .setObserverClock(clock)
        .setObserverRefreshInterval(1)
        .build();

    when(screen.positionOf(any(Supplier.class)))
        .thenReturn(Optional.empty())
        .thenReturn(Optional.empty())
        .thenReturn(Optional.of(clickPosition));

    Optional<Position> result = sut.clickButton(() -> new Image(new int[]{0}, 1, 1), 2);

    assertThat(result).isPresent().contains(clickPosition);
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).move(clickPosition.getX(), clickPosition.getY());
    inOrder.verify(executor).press(MouseButton.LEFT);
    inOrder.verify(executor).release(MouseButton.LEFT);
    verifyNoMoreInteractions(executor);
  }
}