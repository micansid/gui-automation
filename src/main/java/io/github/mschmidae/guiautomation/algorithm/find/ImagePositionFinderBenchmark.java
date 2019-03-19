package io.github.mschmidae.guiautomation.algorithm.find;

import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.function.TriFunction;
import io.github.mschmidae.guiautomation.util.helper.Ensure;
import io.github.mschmidae.guiautomation.util.helper.StopWatch;
import io.github.mschmidae.guiautomation.util.image.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public class ImagePositionFinderBenchmark implements ImagePositionFinder {
  private final Map<ImagePositionFinder, StopWatch> benchmarkUnit = new HashMap<>();

  public ImagePositionFinderBenchmark(final ImagePositionFinder...  finders) {
    Ensure.notEmpty(finders);
    for (ImagePositionFinder finder : finders) {
      getBenchmarkUnit().put(finder, new StopWatch(System::nanoTime));
    }
  }

  @Override
  public Optional<Position> find(final Image image, final Image pattern) {
    return runOnEachFinder(ImagePositionFinder::find, image, pattern,
        "The results of the find() method from the ImagePositionFinders differ");
  }

  @Override
  public List<Position> findAll(final Image image, final Image pattern) {
    return runOnEachFinder(ImagePositionFinder::findAll, image, pattern,
        "The results of the findAll() method from the ImagePositionFinders differ");
  }

  @Override
  public Map<Image, List<Position>> findAll(final Image image, final Set<Image> patterns) {
    return runOnEachFinder(ImagePositionFinder::findAll, image, patterns,
        "The results of the findAll() method from the ImagePositionFinders differ");
  }

  @Override
  public boolean at(final Image image, final Image pattern, final Position position) {
    List<Boolean> results = new ArrayList<>();

    for (Map.Entry<ImagePositionFinder, StopWatch> unit : getBenchmarkUnit().entrySet()) {
      unit.getValue().start();
      results.add(unit.getKey().at(image, pattern, position));
      unit.getValue().pause();
    }

    for (Boolean result : results) {
      if (!result.equals(results.get(0))) {
        throw new RuntimeException("The results of the at method from the ImagePositionFinders differ");
      }
    }
    return results.get(0);
  }

  @Override
  public boolean at(final Image image, final Image pattern, final int positionX,
                    final int positionY) {
    List<Boolean> results = new ArrayList<>();

    for (Map.Entry<ImagePositionFinder, StopWatch> unit : getBenchmarkUnit().entrySet()) {
      unit.getValue().start();
      results.add(unit.getKey().at(image, pattern, positionX, positionY));
      unit.getValue().pause();
    }

    for (Boolean result : results) {
      if (!result.equals(results.get(0))) {
        throw new RuntimeException("The results of the at method from the ImagePositionFinders differ");
      }
    }
    return results.get(0);
  }


  public String result() {
    return benchmarkResultMilliSeconds().entrySet()
        .stream().map(unit -> unit.getKey() + " - " + unit.getValue())
        .collect(Collectors.joining("\n"));
  }

  @Override
  public String toString() {
    return result();
  }

  public Map<Class<? extends ImagePositionFinder>, Long> benchmarkResultNanoSeconds() {
    return getBenchmarkUnit().entrySet().stream()
        .collect(Collectors.toMap(unit -> unit.getKey().getClass(),
            unit -> unit.getValue().duration()));
  }

  public Map<Class<? extends ImagePositionFinder>, Double> benchmarkResultMilliSeconds() {
    return getBenchmarkUnit().entrySet().stream()
        .collect(Collectors.toMap(unit -> unit.getKey().getClass(),
            unit -> ((double)unit.getValue().duration()) / 1_000_000));
  }

  private <T, U> U runOnEachFinder(final TriFunction<ImagePositionFinder,Image, T, U> method,
                                   final Image image,
                                   final T parameter, final String message) {
    List<U> results = new ArrayList<>();

    for (Map.Entry<ImagePositionFinder, StopWatch> unit : getBenchmarkUnit().entrySet()) {
      unit.getValue().start();
      results.add(method.apply(unit.getKey(), image, parameter));
      unit.getValue().pause();
    }

    for (U result : results) {
      if (!result.equals(results.get(0))) {
        throw new RuntimeException(message);
      }
    }
    return results.get(0);
  }
}
