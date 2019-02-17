package io.github.mschmidae.guiautomation.control.keyboard;

public interface KeyboardCommandExecutor {
  /**
   * Execute the press of the given key.
   * @param key to press
   */
  void press(Key key);

  /**
   * Execute the release of the given key.
   * @param key to release
   */
  void release(Key key);
}
