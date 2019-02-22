package io.github.mschmidae.guiautomation.control.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Optional;

public class AwtClipboardCommandExecutor implements ClipboardCommandExecutor {
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
}
