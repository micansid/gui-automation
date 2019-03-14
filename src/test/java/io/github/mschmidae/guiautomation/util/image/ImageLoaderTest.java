package io.github.mschmidae.guiautomation.util.image;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ImageLoaderTest {
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
}