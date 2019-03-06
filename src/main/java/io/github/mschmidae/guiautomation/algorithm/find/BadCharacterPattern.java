package io.github.mschmidae.guiautomation.algorithm.find;

import io.github.mschmidae.guiautomation.util.helper.Ensure;
import io.github.mschmidae.guiautomation.util.image.Image;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Delegate;

public class BadCharacterPattern {
  @Delegate
  private final Image image;
  @Getter(AccessLevel.PRIVATE)
  private final PatternLine line;

  public BadCharacterPattern(final Image image, final int checkLine) {
    Ensure.notNull(image);
    Ensure.notNegative(checkLine);
    Ensure.smaller(checkLine, image.getHeight());

    this.image = image;
    line = new PatternLine(image, checkLine);
  }

  public BadCharacterPattern(final Image image) {
    Ensure.notNull(image);

    this.image = image;
    line = bestDeltaLine(image);
  }

  public int getLineIndex() {
    return getLine().getLineIndex();
  }

  public int getTransparentOffset() {
    return getLine().getTransparentOffset();
  }

  public boolean containsTransparent() {
    return getLine().getTransparentOffset() != 0;
  }

  public Map<Integer, Integer> getColorDelta() {
    return getLine().getColorDelta();
  }

  private PatternLine bestDeltaLine(final Image pattern) {
    return IntStream.range(0, pattern.getHeight())
        .mapToObj(y -> new PatternLine(pattern, y))
        .sorted().findFirst().get();
  }

  private class PatternLine implements Comparable<PatternLine> {
    private final int lineIndex;
    private final int transparentOffset;
    private final Set<Integer> colors;
    private final Map<Integer, Integer> colorDelta;

    private PatternLine(final Image image, final int lineIndex) {
      this.lineIndex = lineIndex;
      transparentOffset = transparentOffset(image, lineIndex);
      colors = colors(image, lineIndex);
      colorDelta = colorDelta(image, lineIndex);
    }

    @Override
    public int compareTo(PatternLine patternLine) {
      Ensure.notNull(patternLine);

      int result = 0;
      if (getTransparentOffset() == 0 && patternLine.getTransparentOffset() != 0) {
        result = -1;
      } else if (getTransparentOffset() != 0 && patternLine.getTransparentOffset() == 0) {
        result = 1;
      } else {
        result = patternLine.getTransparentOffset() - getTransparentOffset();
      }
/*
            if (result == 0) {
                System.out.println(patternLine.getColorDelta());
                System.out.println(getColorDelta());
                System.out.println(patternLine.getColorDelta().values());
                System.out.println(getColorDelta().values());
                result = patternLine.getColorDelta().values().stream().mapToInt(v -> v).min().getAsInt() - getColorDelta().values().stream().mapToInt(v -> v).max().getAsInt();
            }

            if (result == 0) {
                result = patternLine.getDifferentColorCount() - getDifferentColorCount();
            }
*/
      return result;
    }

    private int transparentOffset(final Image image, final int patternLineIndex) {
      int result = 0;
      for (int index = 0; index < image.getWidth() && result == 0; index++) {
        if (image.isTransparent(image.getWidth() - index - 1, patternLineIndex)) {
          result = index + 1;
        }
      }
      return result;
    }

    private Set<Integer> colors(final Image image, final int lineIndex) {
      Set<Integer> colors = new HashSet<>();
      for (int x = 0; x < image.getWidth(); x++) {
        if (!image.isTransparent(x, lineIndex)) {
          colors.add(image.getRgb(x, lineIndex));
        }
      }
      return colors;
    }

    private Map<Integer, Integer> colorDelta(final Image pattern, final int patternLineIndex) {
      HashMap<Integer, Integer> colorDelta = new HashMap<>();
      for (int x = 0; x < pattern.getWidth(); x++) {
        colorDelta.put(pattern.getRgb(x, patternLineIndex), pattern.getWidth() - x - 1);
      }
      colorDelta.remove(pattern.getRgb(pattern.getWidth() - 1, patternLineIndex));

      return Collections.unmodifiableMap(colorDelta);
    }

    private int getLineIndex() {
      return lineIndex;
    }

    private int getTransparentOffset() {
      return transparentOffset;
    }

    private int getDifferentColorCount() {
      return colors.size();
    }

    public Map<Integer, Integer> getColorDelta() {
      return colorDelta;
    }
  }
}
