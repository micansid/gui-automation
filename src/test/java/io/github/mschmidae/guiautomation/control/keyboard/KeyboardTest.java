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

  @Test
  void typeTextWithSmallAndBigLettersAndNumbers() {
    KeyboardCommandExecutor executor = mock(KeyboardCommandExecutor.class);
    Keyboard sut = new Keyboard(executor);
    sut.type("1bC4eF");

    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).press(Key.NUM_1);
    inOrder.verify(executor).release(Key.NUM_1);
    inOrder.verify(executor).press(Key.B);
    inOrder.verify(executor).release(Key.B);
    inOrder.verify(executor).press(Key.SHIFT);
    inOrder.verify(executor).press(Key.C);
    inOrder.verify(executor).release(Key.C);
    inOrder.verify(executor).release(Key.SHIFT);
    inOrder.verify(executor).press(Key.NUM_4);
    inOrder.verify(executor).release(Key.NUM_4);
    inOrder.verify(executor).press(Key.E);
    inOrder.verify(executor).release(Key.E);
    inOrder.verify(executor).press(Key.SHIFT);
    inOrder.verify(executor).press(Key.F);
    inOrder.verify(executor).release(Key.F);
    inOrder.verify(executor).release(Key.SHIFT);
    verifyNoMoreInteractions(executor);
  }

  @Test
  void typeSpaceDotColonSlashPlusMinus() {
    KeyboardCommandExecutor executor = mock(KeyboardCommandExecutor.class);
    Keyboard sut = new Keyboard(executor);
    sut.type(" .,/+-");

    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).press(Key.SPACE);
    inOrder.verify(executor).release(Key.SPACE);
    inOrder.verify(executor).press(Key.PERIOD);
    inOrder.verify(executor).release(Key.PERIOD);
    inOrder.verify(executor).press(Key.COLON);
    inOrder.verify(executor).release(Key.COLON);
    inOrder.verify(executor).press(Key.SLASH);
    inOrder.verify(executor).release(Key.SLASH);
    inOrder.verify(executor).press(Key.PLUS);
    inOrder.verify(executor).release(Key.PLUS);
    inOrder.verify(executor).press(Key.MINUS);
    inOrder.verify(executor).release(Key.MINUS);
    verifyNoMoreInteractions(executor);
  }
}