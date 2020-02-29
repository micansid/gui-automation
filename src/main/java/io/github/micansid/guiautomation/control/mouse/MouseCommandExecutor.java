package io.github.micansid.guiautomation.control.mouse;

public interface MouseCommandExecutor {
  void move(int x, int y);

  void press(MouseButton button);

  void release(MouseButton button);

  void scroll(int notches);
}
