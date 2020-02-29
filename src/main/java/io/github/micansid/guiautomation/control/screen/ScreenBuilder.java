package io.github.micansid.guiautomation.control.screen;

import io.github.micansid.guiautomation.algorithm.find.ImagePositionFinder;
import io.github.micansid.guiautomation.algorithm.find.SimpleFinder;
import io.github.micansid.guiautomation.control.awt.AwtScreenshotSupplier;
import io.github.micansid.guiautomation.util.helper.Ensure;
import io.github.micansid.guiautomation.util.image.Image;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class ScreenBuilder {
  private Supplier<Image> screenSupplier = new AwtScreenshotSupplier();
  private ImagePositionFinder finder = new SimpleFinder();

  public Screen build() {
    return new Screen(getFinder(), getScreenSupplier());
  }

  public ScreenBuilder setScreenSupplier(final Supplier<Image> screenSupplier) {
    Ensure.notNull(screenSupplier);
    this.screenSupplier = screenSupplier;
    return this;
  }

  public ScreenBuilder setFinder(final ImagePositionFinder finder) {
    Ensure.notNull(finder);
    this.finder = finder;
    return this;
  }
}
