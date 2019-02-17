package io.github.mschmidae.guiautomation.control.keyboard;

import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class KeyboardTest {
  @Test
  void pressTest() {
    KeyboardCommandExecutor executor = mock(KeyboardCommandExecutor.class);
    Keyboard sut = new Keyboard(executor);
    sut.press(Key.SPACE);
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).press(Key.SPACE);
    verifyNoMoreInteractions(executor);
  }

  @Test
  void releaseTest() {
    KeyboardCommandExecutor executor = mock(KeyboardCommandExecutor.class);
    Keyboard sut = new Keyboard(executor);
    sut.release(Key.SPACE);
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).release(Key.SPACE);
    verifyNoMoreInteractions(executor);
  }

  @Test
  void inputTest() {
    KeyboardCommandExecutor executor = mock(KeyboardCommandExecutor.class);
    Keyboard sut = new Keyboard(executor);
    sut.input(Key.SPACE);
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).press(Key.SPACE);
    inOrder.verify(executor).release(Key.SPACE);
    verifyNoMoreInteractions(executor);
  }

  @Test
  void executeTest() {
    KeyboardCommandExecutor executor = mock(KeyboardCommandExecutor.class);
    Keyboard sut = new Keyboard(executor);
    Consumer<Keyboard> shortcut = keyboard -> keyboard
        .press(Key.CONTROL).input(Key.A).release(Key.CONTROL);
    sut.execute(shortcut);
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).press(Key.CONTROL);
    inOrder.verify(executor).press(Key.A);
    inOrder.verify(executor).release(Key.A);
    inOrder.verify(executor).release(Key.CONTROL);
    verifyNoMoreInteractions(executor);
  }
}