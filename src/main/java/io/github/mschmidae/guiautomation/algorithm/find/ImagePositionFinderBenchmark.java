package io.github.mschmidae.guiautomation.algorithm.find;

import io.github.mschmidae.guiautomation.util.Position;
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
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public class ImagePositionFinderBenchmark implements ImagePositionFinder {
  private final Map<ImagePositionFinder, StopWatch> benchmarkUnit = new HashMap<>();

  public ImagePositionFinderBenchmark(ImagePositionFinder...  finders) {
    Ensure.notEmpty(finders);
    for (ImagePositionFinder finder : finders) {
      getBenchmarkUnit().put(finder, new StopWatch(System::nanoTime));
    }
  }

  @Override
  public Optional<Position> find(Image image, Image pattern) {
    List<Optional<Position>> results = new ArrayList<>();

    for (Map.Entry<ImagePositionFinder, StopWatch> unit : getBenchmarkUnit().entrySet()) {
      unit.getValue().start();
      results.add(unit.getKey().find(image, pattern));
      unit.getValue().pause();
    }

    for (Optional<Position> result : results) {
      if (!result.equals(results.get(0))) {
        throw new RuntimeException("The results of the find method from the ImagePositionFinders differ");
      }
    }
    return results.get(0);
  }

  @Override
  public List<Position> findAll(Image image, Image pattern) {
    List<List<Position>> results = new ArrayList<>();

    for (Map.Entry<ImagePositionFinder, StopWatch> unit : getBenchmarkUnit().entrySet()) {
      unit.getValue().start();
      results.add(unit.getKey().findAll(image, pattern));
      unit.getValue().pause();
    }

    for (List<Position> result : results) {
      if (!result.equals(results.get(0))) {
        throw new RuntimeException("The results of the findAll method from the ImagePositionFinders differ");
      }
    }
    return results.get(0);
  }

  @Override
  public Map<Image, List<Position>> findAll(Image image, Set<Image> patterns) {
    List<Map<Image, List<Position>>> results = new ArrayList<>();

    for (Map.Entry<ImagePositionFinder, StopWatch> unit : getBenchmarkUnit().entrySet()) {
      unit.getValue().start();
      results.add(unit.getKey().findAll(image, patterns));
      unit.getValue().pause();
    }

    for (Map<Image, List<Position>> result : results) {
      if (!result.equals(results.get(0))) {
        throw new RuntimeException("The results of the findAll method from the ImagePositionFinders differ");
      }
    }
    return results.get(0);
  }

  @Override
  public boolean at(Image image, Image pattern, Position position) {
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
  public boolean at(Image image, Image pattern, int positionX, int positionY) {
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


  public void printResult() {
    for (Map.Entry<Class<? extends ImagePositionFinder>, Double> unit : benchmarkResultMilliSeconds().entrySet()) {
      System.out.println(unit.getKey() + " - " + unit.getValue());
    }
  }

  public Map<Class<? extends ImagePositionFinder>, Long> benchmarkResultNanoSeconds() {
    HashMap<Class<? extends ImagePositionFinder>, Long> result = new HashMap<>();
    for (Map.Entry<ImagePositionFinder, StopWatch> unit : getBenchmarkUnit().entrySet()) {
      result.put(unit.getKey().getClass(), unit.getValue().duration());
    }
    return result;
  }

  public Map<Class<? extends ImagePositionFinder>, Double> benchmarkResultMilliSeconds() {
    HashMap<Class<? extends ImagePositionFinder>, Double> result = new HashMap<>();
    for (Map.Entry<ImagePositionFinder, StopWatch> unit : getBenchmarkUnit().entrySet()) {
      result.put(unit.getKey().getClass(), ((double)unit.getValue().duration()) / 1_000_000);
    }
    return result;
  }

  private <T, U> U runOnEachFinder(BiFunction<BufferedImage, T, U> method, BufferedImage image, T pattern) {
    List<U> results = new ArrayList<>();

    for (Map.Entry<ImagePositionFinder, StopWatch> unit : getBenchmarkUnit().entrySet()) {
      unit.getValue().start();
      results.add(method.apply(image, pattern));
      unit.getValue().pause();
    }

    for (U result : results) {
      if (!result.equals(results.get(0))) {
        throw new RuntimeException("The results of the find method from the ImagePositionFinders differ");
      }
    }
    return results.get(0);
  }
}
