package io.github.mschmidae.guiautomation.control.mouse;

import io.github.mschmidae.guiautomation.util.helper.Ensure;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.HashMap;
import java.util.Map;


public class AwtMouseCommandExecutor implements MouseCommandExecutor {
    private static final Map<MouseButton, Integer> MAPPING = new HashMap<>();

    static {
        MAPPING.put(MouseButton.LEFT, InputEvent.BUTTON1_MASK);
        MAPPING.put(MouseButton.RIGHT, InputEvent.BUTTON2_MASK);
        MAPPING.put(MouseButton.MIDDLE, InputEvent.BUTTON3_MASK);
    }

    private final Robot robot;

    public AwtMouseCommandExecutor() {
        Robot newRobot;
        try {
            newRobot = new Robot();
        } catch (AWTException exception) {
            throw new RuntimeException(exception);
        }
        robot = newRobot;
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

    private Robot getRobot() {
        return robot;
    }
}
