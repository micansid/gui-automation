package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.util.image.Image;
import io.github.mschmidae.guiautomation.util.image.ImageExporter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

class AwtScreenshotSupplierTest {
  @Disabled
  @Test
  void takeScreenshotAndExport() {
    Supplier<Image> screenshotSupplier = new AwtScreenshotSupplier();
    Image image = screenshotSupplier.get();

    ImageExporter exporter = new ImageExporter();
    exporter.export(image, "takeScreenshotAndExport");
  }
}