package io.github.mschmidae.guiautomation.control;

import io.github.mschmidae.guiautomation.control.clipboard.Clipboard;
import io.github.mschmidae.guiautomation.control.keyboard.Key;
import io.github.mschmidae.guiautomation.control.keyboard.Keyboard;
import io.github.mschmidae.guiautomation.control.keyboard.KeyboardCommandExecutor;
import io.github.mschmidae.guiautomation.control.mouse.Mouse;
import io.github.mschmidae.guiautomation.control.screen.Screen;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;


import static org.assertj.core.api.Assertions.assertThat;
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

    Controller sut = new Controller(clipboard, new Keyboard(executor), mock(Mouse.class),
        mock(Screen.class));

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

    Controller sut = new Controller(clipboard, new Keyboard(executor), mock(Mouse.class),
        mock(Screen.class));
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

}