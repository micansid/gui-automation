package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.algorithm.find.ImagePositionFinder;
import io.github.mschmidae.guiautomation.algorithm.find.SimpleFinder;
import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.Section;
import io.github.mschmidae.guiautomation.util.helper.Ensure;
import io.github.mschmidae.guiautomation.util.image.Image;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Screen {
    private final Supplier<Image> screenSupplier;
    private final ImagePositionFinder finder;

    public Screen() {
        this(new AwtScreenshotSupplier());
    }

    public Screen(final Supplier<Image> screenSupplier) {
        this(new SimpleFinder(), screenSupplier);
    }

    public Screen(final ImagePositionFinder finder, final Supplier<Image> screenSupplier) {
        Ensure.notNull(finder);
        Ensure.notNull(screenSupplier);

        this.finder = finder;
        this.screenSupplier = screenSupplier;
    }

    public Optional<Position> positionOf(final Supplier<Image> supplier) {
        Ensure.notNull(supplier);
        Image image = supplier.get();
        Ensure.notNull(image);

        Image screen = getScreenSupplier().get();
        return getFinder().find(screen, image);
    }

    public Optional<Position> positionOf(final Supplier<Image> supplier, final Section section) {
        Ensure.notNull(supplier);
        Image image = supplier.get();
        Ensure.notNull(image);
        Ensure.notNull(section);

        Image screen = getScreenSupplier().get().getSubImage(section);
        Optional<Position> subPosition = getFinder().find(screen, supplier.get());
        Optional<Position> result = Optional.empty();
        if (subPosition.isPresent()) {
            result = Optional.of(section.scaleUpPosition(subPosition.get()));
        }

        return result;
    }

    public Optional<Position> positionOf(final List<Supplier<Image>> suppliers) {
        Ensure.notNull(suppliers);
        List<Image> images = suppliers.stream()
                .map(Supplier::get)
                .collect(Collectors.toList());
        Ensure.notNull(images);

        Image screen = getScreenSupplier().get();
        return images.stream()
                .flatMap(pattern -> getFinder().findAll(screen, pattern).stream())
                .findFirst();
    }

    private Supplier<Image> getScreenSupplier() {
        return screenSupplier;
    }

    private ImagePositionFinder getFinder() {
        return finder;
    }
}
