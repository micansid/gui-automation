package io.github.micansid.guiautomation.algorithm.find;

import io.github.micansid.guiautomation.util.Position;
import io.github.micansid.guiautomation.util.image.Image;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ImagePositionFinderBenchmarkTest {
  private final static Image TEST_IMAGE = new Image(new int[]{0}, 1, 1);
  private final static Position TEST_POSITION = new Position(0, 0);

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @BeforeEach
  void setUpStreams() {
    System.setOut(new PrintStream(getOutContent()));
    System.setErr(new PrintStream(getErrContent()));
  }

  @AfterEach
  void restoreStreams() {
    System.setOut(getOriginalOut());
    System.setErr(getOriginalErr());
  }

  @Test
  void findWithSameResult() {
    ImagePositionFinder finder1 = mock(SimpleFinder.class);
    ImagePositionFinder finder2 = mock(ImagePositionFinder.class);
    when(finder1.find(TEST_IMAGE, TEST_IMAGE)).thenReturn(Optional.of(TEST_POSITION));
    when(finder2.find(TEST_IMAGE, TEST_IMAGE)).thenReturn(Optional.of(TEST_POSITION));

    ImagePositionFinderBenchmark finderBenchmark = new ImagePositionFinderBenchmark(finder1, finder2);

    Assertions.assertThat(finderBenchmark.find(TEST_IMAGE, TEST_IMAGE)).isPresent().contains(TEST_POSITION);
    assertThat(finderBenchmark.benchmarkResultNanoSeconds()).containsKeys(finder1.getClass(), finder2.getClass());
    assertThat(finderBenchmark.benchmarkResultMilliSeconds()).containsKeys(finder1.getClass(), finder2.getClass());
    System.out.println(finderBenchmark);
  }

  @Test
  void findWithDifferentResults() {
    ImagePositionFinder finder1 = mock(SimpleFinder.class);
    ImagePositionFinder finder2 = mock(ImagePositionFinder.class);
    when(finder1.find(TEST_IMAGE, TEST_IMAGE)).thenReturn(Optional.of(TEST_POSITION));
    when(finder2.find(TEST_IMAGE, TEST_IMAGE)).thenReturn(Optional.of(new Position(1, 0)));

    ImagePositionFinderBenchmark finderBenchmark = new ImagePositionFinderBenchmark("target", finder1, finder2);

    assertThatThrownBy(() -> finderBenchmark.find(TEST_IMAGE, TEST_IMAGE))
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  void findAllOnePatternWithSameResult() {
    ImagePositionFinder finder1 = mock(SimpleFinder.class);
    ImagePositionFinder finder2 = mock(ImagePositionFinder.class);
    when(finder1.findAll(TEST_IMAGE, TEST_IMAGE)).thenReturn(Arrays.asList(TEST_POSITION));
    when(finder2.findAll(TEST_IMAGE, TEST_IMAGE)).thenReturn(Arrays.asList(TEST_POSITION));

    ImagePositionFinderBenchmark finderBenchmark = new ImagePositionFinderBenchmark(finder1, finder2);

    Assertions.assertThat(finderBenchmark.findAll(TEST_IMAGE, TEST_IMAGE)).hasSize(1).contains(TEST_POSITION);
    assertThat(finderBenchmark.benchmarkResultNanoSeconds()).containsKeys(finder1.getClass(), finder2.getClass());
    assertThat(finderBenchmark.benchmarkResultMilliSeconds()).containsKeys(finder1.getClass(), finder2.getClass());
    System.out.println(finderBenchmark);
  }

  @Test
  void findAllOnePatternWithDifferentResults() {
    ImagePositionFinder finder1 = mock(SimpleFinder.class);
    ImagePositionFinder finder2 = mock(ImagePositionFinder.class);
    when(finder1.findAll(TEST_IMAGE, TEST_IMAGE)).thenReturn(Arrays.asList(TEST_POSITION));
    when(finder2.findAll(TEST_IMAGE, TEST_IMAGE)).thenReturn(Arrays.asList(new Position(1, 0)));

    ImagePositionFinderBenchmark finderBenchmark = new ImagePositionFinderBenchmark("target", finder1, finder2);

    assertThatThrownBy(() -> finderBenchmark.findAll(TEST_IMAGE, TEST_IMAGE))
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  void findAllSetOfPatternWithSameResult() {
    Set<Image> patternSet = new HashSet<>();
    patternSet.add(TEST_IMAGE);
    Map<Image, List<Position>> resultMap = new HashMap<>();
    resultMap.put(TEST_IMAGE, Arrays.asList(TEST_POSITION));
    ImagePositionFinder finder1 = mock(SimpleFinder.class);
    ImagePositionFinder finder2 = mock(ImagePositionFinder.class);
    when(finder1.findAll(TEST_IMAGE, patternSet)).thenReturn(resultMap);
    when(finder2.findAll(TEST_IMAGE, patternSet)).thenReturn(resultMap);

    ImagePositionFinderBenchmark finderBenchmark = new ImagePositionFinderBenchmark(finder1, finder2);

    Assertions.assertThat(finderBenchmark.findAll(TEST_IMAGE, patternSet)).hasSize(1).isEqualTo(resultMap);
    assertThat(finderBenchmark.benchmarkResultNanoSeconds()).containsKeys(finder1.getClass(), finder2.getClass());
    assertThat(finderBenchmark.benchmarkResultMilliSeconds()).containsKeys(finder1.getClass(), finder2.getClass());
    System.out.println(finderBenchmark);
  }

  @Test
  void findAllSetOfPatternDifferentResults() {
    Set<Image> patternSet = new HashSet<>();
    patternSet.add(TEST_IMAGE);
    Map<Image, List<Position>> resultMap = new HashMap<>();
    resultMap.put(TEST_IMAGE, Arrays.asList(TEST_POSITION));
    ImagePositionFinder finder1 = mock(SimpleFinder.class);
    ImagePositionFinder finder2 = mock(ImagePositionFinder.class);
    when(finder1.findAll(TEST_IMAGE, patternSet)).thenReturn(resultMap);
    when(finder2.findAll(TEST_IMAGE, patternSet)).thenReturn(new HashMap<>());

    ImagePositionFinderBenchmark finderBenchmark = new ImagePositionFinderBenchmark("target", finder1, finder2);

    assertThatThrownBy(() -> finderBenchmark.findAll(TEST_IMAGE, patternSet))
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  void atWithSameResult() {
    ImagePositionFinder finder1 = mock(SimpleFinder.class);
    ImagePositionFinder finder2 = mock(ImagePositionFinder.class);
    when(finder1.at(TEST_IMAGE, TEST_IMAGE, TEST_POSITION)).thenReturn(true);
    when(finder1.at(TEST_IMAGE, TEST_IMAGE, TEST_POSITION.getX(), TEST_POSITION.getY())).thenReturn(true);
    when(finder2.at(TEST_IMAGE, TEST_IMAGE, TEST_POSITION)).thenReturn(true);
    when(finder2.at(TEST_IMAGE, TEST_IMAGE, TEST_POSITION.getX(), TEST_POSITION.getY())).thenReturn(true);

    ImagePositionFinderBenchmark finderBenchmark = new ImagePositionFinderBenchmark(finder1, finder2);

    assertThat(finderBenchmark.at(TEST_IMAGE, TEST_IMAGE, TEST_POSITION)).isTrue();
    assertThat(finderBenchmark.at(TEST_IMAGE, TEST_IMAGE, TEST_POSITION.getX(), TEST_POSITION.getY())).isTrue();
    assertThat(finderBenchmark.benchmarkResultNanoSeconds()).containsKeys(finder1.getClass(), finder2.getClass());
    assertThat(finderBenchmark.benchmarkResultMilliSeconds()).containsKeys(finder1.getClass(), finder2.getClass());
    System.out.println(finderBenchmark);
  }

  @Test
  void atWithSameDifferentResults() {
    ImagePositionFinder finder1 = mock(SimpleFinder.class);
    ImagePositionFinder finder2 = mock(ImagePositionFinder.class);
    when(finder1.at(TEST_IMAGE, TEST_IMAGE, TEST_POSITION)).thenReturn(true);
    when(finder1.at(TEST_IMAGE, TEST_IMAGE, TEST_POSITION.getX(), TEST_POSITION.getY())).thenReturn(true);
    when(finder2.at(TEST_IMAGE, TEST_IMAGE, TEST_POSITION)).thenReturn(false);
    when(finder2.at(TEST_IMAGE, TEST_IMAGE, TEST_POSITION.getX(), TEST_POSITION.getY())).thenReturn(false);

    ImagePositionFinderBenchmark finderBenchmark = new ImagePositionFinderBenchmark("target", finder1, finder2);

    assertThatThrownBy(() -> finderBenchmark.at(TEST_IMAGE, TEST_IMAGE, TEST_POSITION))
        .isInstanceOf(RuntimeException.class);
    assertThatThrownBy(() -> finderBenchmark.at(TEST_IMAGE, TEST_IMAGE, TEST_POSITION.getX(), TEST_POSITION.getY()))
        .isInstanceOf(RuntimeException.class);
  }

  private ByteArrayOutputStream getOutContent() {
    return outContent;
  }

  private ByteArrayOutputStream getErrContent() {
    return errContent;
  }

  private PrintStream getOriginalOut() {
    return originalOut;
  }

  private PrintStream getOriginalErr() {
    return originalErr;
  }
}