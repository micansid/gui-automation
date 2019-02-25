package io.github.mschmidae.guiautomation.control.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Optional;

public class AwtClipboardCommandExecutor implements ClipboardCommandExecutor, ClipboardOwner {
  @Override
  public void accept(final String data) {
    StringSelection selection = new StringSelection(data);
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
  }

  @Override
  public Optional<String> get() {
    Optional<String> result = Optional.empty();
    try {
      Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
      if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
        String data = (String) t.getTransferData(DataFlavor.stringFlavor);
        result = Optional.of(data);
      }
    } catch (UnsupportedFlavorException | IOException ex) {
      // method returns empty Optional, when an exception is thrown while fetching data from
      // clipboard
    }
    return result;
  }

  /**
   * Empty implementation of the ClipboardOwner interface.
   * https://stackoverflow.com/questions/19611805/cannot-open-system-clipboard-exception-thrown-on-setcaretposition
   * @param clipboard dummy
   * @param contents dummy
   */
  @Override
  public void lostOwnership(final Clipboard clipboard, final Transferable contents) {
    //do nothing
  }
}
