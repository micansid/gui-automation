package io.github.mschmidae.guiautomation.control.awt;

import io.github.mschmidae.guiautomation.control.keyboard.Key;
import io.github.mschmidae.guiautomation.control.keyboard.KeyboardCommandExecutor;
import io.github.mschmidae.guiautomation.util.helper.Ensure;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public class AwtKeyboardCommandExecutor extends AwtRobotCommandExecutor
    implements KeyboardCommandExecutor {
  private final AwtKeyMapping mapping = new AwtKeyMapping();

  @Override
  public void press(Key key) {
    Ensure.notNull(key);
    getRobot().keyPress(getMapping().map(key));
  }

  @Override
  public void release(Key key) {
    Ensure.notNull(key);
    getRobot().keyRelease(getMapping().map(key));
  }
}
