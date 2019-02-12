package io.github.mschmidae.guiautomation.control.mouse;

import io.github.mschmidae.guiautomation.util.Position;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

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