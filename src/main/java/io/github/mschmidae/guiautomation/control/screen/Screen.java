package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.algorithm.find.ImagePositionFinder;
import io.github.mschmidae.guiautomation.algorithm.find.SimpleFinder;
import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.Section;
import io.github.mschmidae.guiautomation.util.helper.Ensure;
import io.github.mschmidae.guiautomation.util.image.Image;

import java.util.*;
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
        suppliers.forEach(Ensure::suppliesNotNull);

        List<Image> images = suppliers.stream()
                .map(Supplier::get)
                .collect(Collectors.toList());

        Image screen = getScreenSupplier().get();
        return images.stream()
                .flatMap(pattern -> getFinder().findAll(screen, pattern).stream())
                .findFirst();
    }

    /*
    ToDo
    public Optional<Position> positionOf(final List<Supplier<Image>> suppliers, final Section section) {
        Ensure.notNull(suppliers);
        List<Image> images = suppliers.stream()
                .map(Supplier::get)
                .collect(Collectors.toList());
        Ensure.notNull(images);
        Ensure.notNull(section);

        Image screen = getScreenSupplier().get().getSubImage(section);
        Optional<ScreenPosition> subPosition = images.stream().flatMap(pattern -> getFinder().findAll(screen, pattern).stream()).findFirst();
        Optional<ScreenPosition> result = Optional.empty();
        if (subPosition.isPresent()) {
            result = Optional.of(section.scaleUpPosition(subPosition.get()));
        }

        return result;
    }
    */

    public List<Position> positionsOf(final Supplier<Image> supplier) {
        Ensure.suppliesNotNull(supplier);

        Image screen = getScreenSupplier().get();
        return getFinder().findAll(screen, supplier.get());
    }

    /*
    ToDo
    public List<Position> positionsOf(final Supplier<Image> supplier, final Section section) {
        Ensure.suppliesNotNull(supplier);
        Ensure.notNull(section);

        Image screen = getScreenSupplier().get().getSubImage(section);

        return getFinder().findAll(screen, supplier.get()).stream()
                .map(section::scaleUpPosition)
                .collect(Collectors.toList());

    }
    */

    public Map<Image, List<Position>> positionsOf(final Set<Supplier<Image>> suppliers) {
        Ensure.containsNoNull(suppliers);
        suppliers.forEach(Ensure::suppliesNotNull);

        Set<Image> images = suppliers.stream().map(Supplier::get).collect(Collectors.toSet());
        Image screen = getScreenSupplier().get();
        return getFinder().findAll(screen, images);
    }

    /*
    ToDo
    public Map<ScreenImage, List<ScreenPosition>> positionsOf(final Set<ScreenImage> images, final ScreenSection section) {
        Ensure.notNull(images);
        Ensure.notNull(section);

        ScreenImage screen = getScreenSupplier().get().getSubImage(section);

        Map<ScreenImage, List<ScreenPosition>> result = new HashMap<>();

        for (Map.Entry<ScreenImage, List<ScreenPosition>> entry: getFinder().findAll(screen, images).entrySet()) {
            result.put(entry.getKey(), entry.getValue().stream()
                    .map(section::scaleUpPosition)
                    .collect(Collectors.toList()));
        }

        return result;
    }
    */

    public Optional<Position> clickPositionOf(final Supplier<Image> supplier) {
        Ensure.suppliesNotNull(supplier);
        return positionOf(supplier).map(position -> position.addSubPosition(supplier.get().middle()));
    }

    public Optional<Position> clickPositionOf(final Supplier<Image> supplier, final Section section) {
        Ensure.suppliesNotNull(supplier);
        Ensure.notNull(section);
        return positionOf(supplier, section).map(position -> position.addSubPosition(supplier.get().middle()));
    }

    /*
    ToDo
    public Optional<Position> clickPositionOf(final List<Supplier<Image>> suppliers) {
        Ensure.notNull(suppliers);
        suppliers.forEach(Ensure::suppliesNotNull);
        return positionOf(suppliers).map(position -> position.addSubPosition(supplier.get().middle()));
    }
    */

    /*
    ToDo
    public Optional<ScreenPosition> clickPositionOf(final List<ScreenImage> images, final ScreenSection section) {
        Ensure.notNull(images);

        ScreenImage screen = getScreenSupplier().get().getSubImage(section);
        Optional<ScreenPosition> imagePosition = images.stream().flatMap(pattern -> getFinder().findAll(screen, pattern).stream()).findFirst();
        Optional<ScreenPosition> result = Optional.empty();

        if (imagePosition.isPresent()) {
            Optional<ScreenImage> image = images.stream().filter(pattern -> getFinder().at(screen, pattern, imagePosition.get())).findFirst();
            if (image.isPresent()) {
                ScreenPosition middleOfImage = new ScreenPosition(image.get().getWidth() / 2, image.get().getHeight() / 2);
                result = Optional.of(section.scaleUpPosition(imagePosition.get().addSubPosition(middleOfImage)));
            } else {
                throw new RuntimeException("Error in finder! A returned position doesn't match any image");
            }
        }

        return result;
    }
    */

    public List<Position> clickPositionsOf(final Supplier<Image> supplier) {
        Ensure.suppliesNotNull(supplier);
        return positionsOf(supplier).stream()
                .map(position -> position.addSubPosition(supplier.get().middle()))
                .collect(Collectors.toList());
    }

    /*
    ToDo
    public List<ScreenPosition> clickPositionsOf(final ScreenImage image, final ScreenSection section) {
        Ensure.notNull(image);
        Ensure.notNull(section);

        ScreenPosition middleOfImage = new ScreenPosition(image.getWidth() / 2, image.getHeight() / 2);
        return positionsOf(image, section).stream().map(position -> position.addSubPosition(middleOfImage)).collect(Collectors.toList());
    }
    */

    public Map<Image, List<Position>> clickPositionsOf(final Set<Supplier<Image>> suppliers) {
        Ensure.containsNoNull(suppliers);
        suppliers.forEach(Ensure::suppliesNotNull);

        Map<Image, List<Position>> result = new HashMap<>();
        for (Map.Entry<Image, List<Position>> entry : positionsOf(suppliers).entrySet()) {
            result.put(entry.getKey(),
                    entry.getValue().stream().map(position -> position
                            .addSubPosition(entry.getKey().middle())).collect(Collectors.toList()));
        }

        return result;
    }

    /*
    ToDo
    public Map<ScreenImage, List<ScreenPosition>> clickPositionsOf(final Set<ScreenImage> images, final ScreenSection section) {
        Ensure.notNull(images);
        Ensure.notNull(section);

        Map<ScreenImage, List<ScreenPosition>> result = new HashMap<>();

        for (Map.Entry<ScreenImage, List<ScreenPosition>> entry: positionsOf(images, section).entrySet()) {
            ScreenPosition middleOfImage = new ScreenPosition(entry.getKey().getWidth() / 2, entry.getKey().getHeight() / 2);
            result.put(entry.getKey(), entry.getValue().stream()
                    .map(position -> position.addSubPosition(middleOfImage))
                    .collect(Collectors.toList()));
        }

        return result;
    }
    */

    public boolean imageAt(final Supplier<Image> supplier, final Position position) {
        Ensure.suppliesNotNull(supplier);
        Ensure.notNull(position);

        Image screen = getScreenSupplier().get();
        return getFinder().at(screen, supplier.get(), position);
    }

    public int getWidth() {
        return getScreenSupplier().get().getWidth();
    }

    public int getHeight() {
        return getScreenSupplier().get().getHeight();
    }

    private Supplier<Image> getScreenSupplier() {
        return screenSupplier;
    }

    private ImagePositionFinder getFinder() {
        return finder;
    }
}
