package io.github.micansid.guiautomation.control.awt;

import java.awt.AWTException;
import java.awt.Robot;

public class AwtRobotCommandExecutor {
  private Robot robot;

  private void initializeRobot() {
    if (robot == null) {
      try {
        robot = new Robot();
      } catch (AWTException exception) {
        throw new RuntimeException(exception);
      }
    }
  }

  public Robot getRobot() {
    initializeRobot();
    return robot;
  }
}
