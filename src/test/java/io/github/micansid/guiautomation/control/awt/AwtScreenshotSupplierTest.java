package io.github.micansid.guiautomation.control.awt;

import io.github.micansid.guiautomation.util.image.ImageExporter;
import io.github.micansid.guiautomation.util.image.Image;

import java.util.function.Supplier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;



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