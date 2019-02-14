package io.github.mschmidae.guiautomation.main;

import io.github.mschmidae.guiautomation.control.mouse.AwtMousePositionSupplier;
import io.github.mschmidae.guiautomation.control.mouse.Mouse;
import io.github.mschmidae.guiautomation.control.screen.Screen;
import io.github.mschmidae.guiautomation.util.Position;
import java.util.function.Supplier;

public class ImagePatternRecorder {
  public static void main(String[] args) {
    Screen screen = new Screen();
    Mouse mouse = new Mouse();
    Supplier<Position> mousePosition = new AwtMousePositionSupplier();

    System.out.println(mousePosition.get());
    System.out.println(mouse.currentPosition());
  }
}
