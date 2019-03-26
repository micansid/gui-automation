package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.algorithm.find.ImagePositionFinder;
import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.image.Image;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScreenBuilderTest {
  @Test
  void builderWithStubsGeneratesScreenWithStubs() {
    ImagePositionFinder finder = mock(ImagePositionFinder.class);
    Supplier<Image> screenSupplier = mock(Supplier.class);
    Image image = new Image(new  int[]{0}, 1, 1);
    Position position = new Position(0, 0);
    Screen sut = new ScreenBuilder()
        .setScreenSupplier(screenSupplier)
        .setFinder(finder)
        .build();
    when(screenSupplier.get()).thenReturn(image);

    sut.imageAt(() -> image, position);
    verify(finder, times(1)).at(image, image, position);
    verify(screenSupplier, times(1)).get();
  }


}