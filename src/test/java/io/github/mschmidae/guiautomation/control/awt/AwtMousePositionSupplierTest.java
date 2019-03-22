package io.github.mschmidae.guiautomation.control.awt;

import io.github.mschmidae.guiautomation.control.awt.AwtMouseCommandExecutor;
import io.github.mschmidae.guiautomation.control.awt.AwtMousePositionSupplier;
import io.github.mschmidae.guiautomation.control.mouse.MouseCommandExecutor;
import io.github.mschmidae.guiautomation.util.Position;
import java.util.function.Supplier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AwtMousePositionSupplierTest {
  @Disabled
  @Test
  void printCurrentPosition() {
    Supplier<Position> positionSupplier = new AwtMousePositionSupplier();
    System.out.println(positionSupplier.get());
  }

  @Disabled
  @Test
  void printCurrentPositionAfterUtopianWideMouseMove() {
    MouseCommandExecutor executor = new AwtMouseCommandExecutor();
    Supplier<Position> positionSupplier = new AwtMousePositionSupplier();
    executor.move(10_000, 10_000);
    System.out.println(positionSupplier.get());
  }
}