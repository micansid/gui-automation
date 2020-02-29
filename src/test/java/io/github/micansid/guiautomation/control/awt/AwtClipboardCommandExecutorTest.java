package io.github.micansid.guiautomation.control.awt;

import io.github.micansid.guiautomation.control.keyboard.Keyboard;
import io.github.micansid.guiautomation.control.keyboard.Shortcut;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class AwtClipboardCommandExecutorTest {
  @Disabled
  @Test
  void setAndGetClipboardTest() {
    AwtClipboardCommandExecutor clipboard = new AwtClipboardCommandExecutor();
    clipboard.accept("TEST");
    assertThat(clipboard.get()).contains("TEST");
  }

  @Disabled
  @Test
  void getClipboardTest() throws InterruptedException {
    AwtClipboardCommandExecutor clipboard = new AwtClipboardCommandExecutor();
    System.out.println("Test started: Copy test to the clipboard (CRTL+C)");
    System.out.println("Waiting 10 seconds");
    Thread.sleep(10_000);
    System.out.println(clipboard.get().get());
    System.out.println("Is the printed text equal to the copied?");
  }

  @Disabled
  @Test
  void setClipboardInsertWithKeyboardTest() throws InterruptedException {
    AwtClipboardCommandExecutor clipboard = new AwtClipboardCommandExecutor();
    clipboard.accept("TEST");
    Keyboard keyboard = new Keyboard();
    System.out.println("Test started: Move the cursor to a text field");
    System.out.println("Waiting 10 seconds");
    Thread.sleep(10_000);
    keyboard.execute(Shortcut.PASTE);
    System.out.println("Is the inserted text \"TEST\"?");
  }

  @Disabled
  @Test
  void copyWithKeyboardReadClipboardTest() throws InterruptedException {
    AwtClipboardCommandExecutor clipboard = new AwtClipboardCommandExecutor();
    Keyboard keyboard = new Keyboard();
    System.out.println("Test started: Mark some text");
    System.out.println("Waiting 10 seconds");
    Thread.sleep(10_000);
    keyboard.execute(Shortcut.COPY);
    System.out.println(clipboard.get().get());
    System.out.println("Is the printed text equal to marked?");
  }

}