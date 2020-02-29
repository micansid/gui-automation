package io.github.micansid.guiautomation.control.awt;

import io.github.micansid.guiautomation.util.Position;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.function.Supplier;

/**
 * Supplier of the current mouse position.
 * Uses the awt MouseInfo to get the current position.
 */
public class AwtMousePositionSupplier implements Supplier<Position> {
  @Override
  public Position get() {
    Point currentPoint = MouseInfo.getPointerInfo().getLocation();
    return new Position(currentPoint.x, currentPoint.y);
  }
}
