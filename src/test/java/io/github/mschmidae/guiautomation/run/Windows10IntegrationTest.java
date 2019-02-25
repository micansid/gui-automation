package io.github.mschmidae.guiautomation.run;

import io.github.mschmidae.guiautomation.control.clipboard.Clipboard;
import io.github.mschmidae.guiautomation.control.keyboard.Key;
import io.github.mschmidae.guiautomation.control.keyboard.Keyboard;
import io.github.mschmidae.guiautomation.control.keyboard.Shortcut;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class Windows10IntegrationTest {
  private static final int SLEEP_TIME = 1_000;

  @Test
  void openNotepadTypeTextCopyToClipboard() throws InterruptedException {
    Keyboard keyboard = new Keyboard();
    Clipboard clipboard = new Clipboard();

    keyboard.press(Key.WINDOWS).input(Key.R).release(Key.WINDOWS);
    Thread.sleep(SLEEP_TIME);
    keyboard.input(Key.N).input(Key.O).input(Key.T).input(Key.E)
        .input(Key.P).input(Key.A).input(Key.D).input(Key.ENTER);

    Thread.sleep(SLEEP_TIME);
    keyboard.press(Key.SHIFT).input(Key.NUM_8).release(Key.SHIFT)
        .input(Key.A).press(Key.SHIFT).input(Key.A).release(Key.SHIFT)
        .input(Key.B).press(Key.SHIFT).input(Key.B).release(Key.SHIFT)
        .press(Key.SHIFT).input(Key.NUM_9).release(Key.SHIFT);

    Thread.sleep(SLEEP_TIME);
    keyboard.execute(Shortcut.MARK_ALL).execute(Shortcut.COPY);
    keyboard.execute(Shortcut.CLOSE).input(Key.TAB).input(Key.ENTER);

    assertThat(clipboard.get()).isPresent().contains("(aAbB)");
  }

  @Test
  void openNotepadInsertFromClipboardModifyTextCopyToClipboard() throws InterruptedException {
    Keyboard keyboard = new Keyboard();
    Clipboard clipboard = new Clipboard();

    keyboard.press(Key.WINDOWS).input(Key.R).release(Key.WINDOWS);
    Thread.sleep(SLEEP_TIME);
    keyboard.input(Key.N).input(Key.O).input(Key.T).input(Key.E)
        .input(Key.P).input(Key.A).input(Key.D).input(Key.ENTER);


    Thread.sleep(SLEEP_TIME);
    clipboard.set("TEST");
    keyboard.execute(Shortcut.PASTE);

    Thread.sleep(SLEEP_TIME);
    //keyboard.input(Key.HOME).input(Key.BEGIN).press(Key.SHIFT).input(Key.NUM_8).release(Key.SHIFT);
    keyboard.input(Key.END).press(Key.SHIFT).input(Key.NUM_9).release(Key.SHIFT);
    /*
    keyboard.press(Key.SHIFT).input(Key.NUM_8).release(Key.SHIFT)
        .input(Key.A).press(Key.SHIFT).input(Key.A).release(Key.SHIFT)
        .input(Key.B).press(Key.SHIFT).input(Key.B).release(Key.SHIFT)
        .press(Key.SHIFT).input(Key.NUM_9).release(Key.SHIFT);
*/

    Thread.sleep(SLEEP_TIME);
    keyboard.execute(Shortcut.MARK_ALL).execute(Shortcut.COPY);
    keyboard.execute(Shortcut.CLOSE).input(Key.TAB).input(Key.ENTER);

    assertThat(clipboard.get()).isPresent().contains("TEST)");
  }
}
