package io.github.mschmidae.guiautomation.control.awt;

import io.github.mschmidae.guiautomation.control.mouse.MouseButton;
import io.github.mschmidae.guiautomation.control.mouse.MouseCommandExecutor;
import io.github.mschmidae.guiautomation.util.helper.Ensure;

import java.awt.event.InputEvent;
import java.util.HashMap;
import java.util.Map;


public class AwtMouseCommandExecutor extends AwtRobotCommandExecutor
    implements MouseCommandExecutor {
  private static final Map<MouseButton, Integer> MAPPING = new HashMap<>();

  static {
    MAPPING.put(MouseButton.LEFT, InputEvent.BUTTON1_MASK);
    MAPPING.put(MouseButton.RIGHT, InputEvent.BUTTON2_MASK);
    MAPPING.put(MouseButton.MIDDLE, InputEvent.BUTTON3_MASK);
  }

  @Override
  public void move(int x, int y) {
    Ensure.notNegative(x);
    Ensure.notNegative(y);
    getRobot().mouseMove(x, y);
  }

  @Override
  public void press(MouseButton button) {
    Ensure.notNull(button);
    getRobot().mousePress(MAPPING.get(button));
  }

  @Override
  public void release(MouseButton button) {
    Ensure.notNull(button);
    getRobot().mouseRelease(MAPPING.get(button));
  }

  @Override
  public void scroll(int notches) {
    getRobot().mouseWheel(notches);
  }
}
