package io.github.mschmidae.guiautomation.control.mouse;

import io.github.mschmidae.guiautomation.util.Position;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class MouseTest {
  @Test
  void moveLeftTest() {
    Mouse mouse = new Mouse(mock(MouseCommandExecutor.class), () -> new Position(0, 0));
    mouse.move(new Position(1, 1)).moveLeft(1);
    assertThat(mouse.getLastMovePosition()).isEqualTo(new Position(0, 1));
    assertThat(mouse.currentPosition()).isEqualTo(new Position(0, 0));
  }

  @Test
  void moveRightTest() {
    Mouse mouse = new Mouse(mock(MouseCommandExecutor.class), () -> new Position(0, 0));
    mouse.move(new Position(1, 1)).moveRight(1);
    assertThat(mouse.getLastMovePosition()).isEqualTo(new Position(2, 1));
    assertThat(mouse.currentPosition()).isEqualTo(new Position(0, 0));
  }

  @Test
  void moveUpTest() {
    Mouse mouse = new Mouse(mock(MouseCommandExecutor.class), () -> new Position(0, 0));
    mouse.move(new Position(1, 1)).moveUp(1);
    assertThat(mouse.getLastMovePosition()).isEqualTo(new Position(1, 0));
    assertThat(mouse.currentPosition()).isEqualTo(new Position(0, 0));
  }

  @Test
  void moveDownTest() {
    Mouse mouse = new Mouse(mock(MouseCommandExecutor.class), () -> new Position(0, 0));
    mouse.move(new Position(1, 1)).moveDown(1);
    assertThat(mouse.getLastMovePosition()).isEqualTo(new Position(1, 2));
    assertThat(mouse.currentPosition()).isEqualTo(new Position(0, 0));
  }

  @Test
  void leftClickTest() {
    MouseCommandExecutor executor = mock(MouseCommandExecutor.class);
    Mouse mouse = new Mouse(executor, () -> new Position(0, 0));
    mouse.leftClick();
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).press(MouseButton.LEFT);
    inOrder.verify(executor).release(MouseButton.LEFT);
    verifyNoMoreInteractions(executor);
  }

  @Test
  void leftDoubleClickTest() {
    MouseCommandExecutor executor = mock(MouseCommandExecutor.class);
    Mouse mouse = new Mouse(executor, () -> new Position(0, 0));
    mouse.leftDoubleClick();
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).press(MouseButton.LEFT);
    inOrder.verify(executor).release(MouseButton.LEFT);
    inOrder.verify(executor).press(MouseButton.LEFT);
    inOrder.verify(executor).release(MouseButton.LEFT);
    verifyNoMoreInteractions(executor);
  }

  @Test
  void middleClickTest() {
    MouseCommandExecutor executor = mock(MouseCommandExecutor.class);
    Mouse mouse = new Mouse(executor, () -> new Position(0, 0));
    mouse.middleClick();
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).press(MouseButton.MIDDLE);
    inOrder.verify(executor).release(MouseButton.MIDDLE);
    verifyNoMoreInteractions(executor);
  }

  @Test
  void rightClickTest() {
    MouseCommandExecutor executor = mock(MouseCommandExecutor.class);
    Mouse mouse = new Mouse(executor, () -> new Position(0, 0));
    mouse.rightClick();
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).press(MouseButton.RIGHT);
    inOrder.verify(executor).release(MouseButton.RIGHT);
    verifyNoMoreInteractions(executor);
  }

  @Test
  void dragAndDropTest() {
    MouseCommandExecutor executor = mock(MouseCommandExecutor.class);
    Position dropPosition = new Position(1, 1);
    Mouse mouse = new Mouse(executor, () -> new Position(0, 0));
    mouse.dragAndDrop(dropPosition);
    InOrder inOrder = inOrder(executor);
    inOrder.verify(executor).press(MouseButton.LEFT);
    inOrder.verify(executor).move(dropPosition.getX(), dropPosition.getY());
    inOrder.verify(executor).release(MouseButton.LEFT);
    verifyNoMoreInteractions(executor);
  }

  @Test
  void scrollDownTest() {
    MouseCommandExecutor executor = mock(MouseCommandExecutor.class);
    Mouse mouse = new Mouse(executor, () -> new Position(0, 0));
    mouse.scrollDown(1);
    verify(executor).scroll(1);
    verifyNoMoreInteractions(executor);
  }

  @Test
  void scrollUpTest() {
    MouseCommandExecutor executor = mock(MouseCommandExecutor.class);
    Mouse mouse = new Mouse(executor, () -> new Position(0, 0));
    mouse.scrollUp(1);
    verify(executor).scroll(-1);
    verifyNoMoreInteractions(executor);
  }
}