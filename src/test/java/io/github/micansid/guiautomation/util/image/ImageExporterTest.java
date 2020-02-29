package io.github.micansid.guiautomation.util.image;

import io.github.micansid.guiautomation.algorithm.find.FinderTestData;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;


class ImageExporterTest {
  @Test
  void exportAndImportImage() {
    ImageExporter exporter = new ImageExporter();
    ImageLoader loader = new ImageLoader();
    Image image = FinderTestData.SCREEN.getImage();
    String exportFile = "target/exportAndImportImage_" + System.currentTimeMillis() +".png";

    exporter.export(image, exportFile);
    Optional<Image> result = loader.load(exportFile);
    Assertions.assertThat(result).isPresent().contains(image);

  }
}