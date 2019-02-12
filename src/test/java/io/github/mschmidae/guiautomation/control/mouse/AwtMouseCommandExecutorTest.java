package io.github.mschmidae.guiautomation.control.mouse;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AwtMouseCommandExecutorTest {
  @Disabled
  @Test
  void clickLeftButton() throws InterruptedException {
    MouseCommandExecutor executor = new AwtMouseCommandExecutor();

    System.out.println("Test started: Move the mouse to a position to verify a click of the left mouse button.");
    System.out.println("Waiting 10 seconds");
    Thread.sleep(10_000);

    System.out.println("Press left button");
    executor.press(MouseButton.LEFT);

    System.out.println("Waiting 1 second");
    Thread.sleep(1_000);

    System.out.println("Release left button");
    executor.release(MouseButton.LEFT);

    System.out.println("Does the test produce the expected result?");
  }

  @Disabled
  @Test
  void moveMouse() throws InterruptedException {
    MouseCommandExecutor executor = new AwtMouseCommandExecutor();

    System.out.println("Test started");

    System.out.println("Wait one second");
    Thread.sleep(1_000);

    System.out.println("Move to (100|100)");
    executor.move(100, 100);

    System.out.println("Wait one second");
    Thread.sleep(1_000);

    System.out.println("Move to (300|100)");
    executor.move(300, 100);

    System.out.println("Wait one second");
    Thread.sleep(1_000);

    System.out.println("Move to (300|300)");
    executor.move(300, 300);

    System.out.println("Wait one second");
    Thread.sleep(1_000);

    System.out.println("Move to (100|300)");
    executor.move(100, 300);

    System.out.println("Wait one second");
    Thread.sleep(1_000);

    System.out.println("Move to (100|100)");
    executor.move(100, 100);

    System.out.println("Test ended");
  }

  @Disabled
  @Test
  void downScoll() throws InterruptedException {
    MouseCommandExecutor executor = new AwtMouseCommandExecutor();

    System.out.println("Test started: Move the mouse to a position to verify a down scroll.");

    System.out.println("Waiting 10 seconds");
    Thread.sleep(10_000);

    executor.scroll(10);

    System.out.println("Does the test produce the expected result?");
  }
}