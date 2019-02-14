package io.github.mschmidae.guiautomation.control.mouse;

import io.github.mschmidae.guiautomation.util.Position;
import java.util.function.Supplier;

public class Mouse {
  private final Supplier<Position> positionSupplier;

  public Mouse () {
    this(new AwtMousePositionSupplier());
  }
  public Mouse(final Supplier<Position> positionSupplier) {
    this.positionSupplier = positionSupplier;
  }

  public Position currentPosition() {
    return getPositionSupplier().get();
  }

  private Supplier<Position> getPositionSupplier() {
    return positionSupplier;
  }
}
