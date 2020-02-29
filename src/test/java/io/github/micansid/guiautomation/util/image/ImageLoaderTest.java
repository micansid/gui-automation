package io.github.micansid.guiautomation.util.image;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ImageLoaderTest {
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
  void fileNotFound() {
    ImageLoader loader = new ImageLoader();
    assertThat(loader.load("target/fileNotFound.png")).isEmpty();
  }

  @Test
  void resourceNotFound() {
    ImageLoader loader = new ImageLoader();
    assertThat(loader.load("resourceNotFound.png")).isEmpty();
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