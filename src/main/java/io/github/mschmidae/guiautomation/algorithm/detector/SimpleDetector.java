package io.github.mschmidae.guiautomation.algorithm.detector;

import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.Section;
import io.github.mschmidae.guiautomation.util.image.Image;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;

public class SimpleDetector implements GeometricalShapeDetector {
  @Override
  public List<Section> detectRectangles(Image image) {
    List<VerticalLine> lines = new ArrayList<>();
    int lastRgb = 0;
    Position start = new Position(0, 0);
    //int y = 0;
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        if (image.getRgb(x, y) != lastRgb) {
          VerticalLine line = new VerticalLine(start, new Position(x, y));
          if (line.length() > 1) {
            lines.add(line);
          }
          start = new Position(x, y);
        }
        lastRgb = image.getRgb(x, y);
      }
      VerticalLine line = new VerticalLine(start, new Position(image.getWidth() - 1, y));
      if (line.length() > 1) {
        lines.add(line);
      }
    }

    System.out.println(lines.size());

    System.out.println(lines.stream()
        .filter(line -> line.getStart().getY() < image.getHeight() - 1)
        .filter(line -> image.getRgb(line.getStart()) == image.getRgb(line.getStart().move(0, 1))
            || (line.getEnd().getX() > 0 && image.getRgb(line.getStart()) == image.getRgb(line.getStart().move(-1, 1))))
        .filter(line -> image.getRgb(line.getEnd()) == image.getRgb(line.getEnd().move(0, 1))
            || (line.getEnd().getX() < image.getWidth() - 1 && image.getRgb(line.getEnd()) == image.getRgb(line.getEnd().move(1, 1))))
        .peek(System.out::println)
        .count());

    return null;
  }

  @EqualsAndHashCode
  private static class VerticalLine {
    private final Position start;
    private final Position end;

    private VerticalLine(Position start, Position end) {
      this.start = start;
      this.end = end;
    }

    private Position getStart() {
      return start;
    }

    private Position getEnd() {
      return end;
    }

    private int length() {
      return end.getX() - start.getX();
    }

    @Override
    public String toString() {
      return "VerticalLine{" +
          "start=" + start +
          ", end=" + end +
          '}';
    }
  }
}
