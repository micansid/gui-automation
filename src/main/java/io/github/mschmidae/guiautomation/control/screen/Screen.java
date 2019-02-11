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

public class Screen implements Supplier<Image>{
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
        Ensure.suppliesNotNull(supplier);
        Image screen = getScreenSupplier().get();
        return getFinder().find(screen, supplier.get());
    }

    public Optional<Position> positionOf(final Supplier<Image> supplier, final Section section) {
        Ensure.suppliesNotNull(supplier);
        Ensure.notNull(section);

        Image screen = getScreenSupplier().get().getSubImage(section);
        return getFinder().find(screen, supplier.get())
                .map(section::scaleUpPosition);
    }

    public Optional<Position> positionOf(final List<Supplier<Image>> suppliers) {
        Ensure.containsNoNull(suppliers);
        suppliers.forEach(Ensure::suppliesNotNull);

        Image screen = getScreenSupplier().get();
        return suppliers.stream()
                .map(Supplier::get)
                .flatMap(pattern -> getFinder().findAll(screen, pattern).stream())
                .findFirst();
    }


    public Optional<Position> positionOf(final List<Supplier<Image>> suppliers, final Section section) {
        Ensure.containsNoNull(suppliers);
        suppliers.forEach(Ensure::suppliesNotNull);
        Ensure.notNull(section);

        Image screen = getScreenSupplier().get().getSubImage(section);
        return suppliers.stream()
                .map(Supplier::get)
                .flatMap(pattern -> getFinder().findAll(screen, pattern).stream())
                .findFirst()
                .map(section::scaleUpPosition);
    }


    public List<Position> positionsOf(final Supplier<Image> supplier) {
        Ensure.suppliesNotNull(supplier);

        Image screen = getScreenSupplier().get();
        return getFinder().findAll(screen, supplier.get());
    }


    public List<Position> positionsOf(final Supplier<Image> supplier, final Section section) {
        Ensure.suppliesNotNull(supplier);
        Ensure.notNull(section);

        Image screen = getScreenSupplier().get().getSubImage(section);

        return getFinder().findAll(screen, supplier.get()).stream()
                .map(section::scaleUpPosition)
                .collect(Collectors.toList());
    }


    public Map<Image, List<Position>> positionsOf(final Set<Supplier<Image>> suppliers) {
        Ensure.containsNoNull(suppliers);
        suppliers.forEach(Ensure::suppliesNotNull);

        Set<Image> images = suppliers.stream().map(Supplier::get).collect(Collectors.toSet());
        Image screen = getScreenSupplier().get();
        return getFinder().findAll(screen, images);
    }

    public Map<Image, List<Position>> positionsOf(final Set<Supplier<Image>> suppliers, final Section section) {
        Ensure.containsNoNull(suppliers);
        suppliers.forEach(Ensure::suppliesNotNull);
        Ensure.notNull(section);

        Set<Image> images = suppliers.stream().map(Supplier::get).collect(Collectors.toSet());
        Image screen = getScreenSupplier().get().getSubImage(section);

        return getFinder().findAll(screen, images).entrySet().stream()
                .peek(entry -> entry.setValue(entry.getValue().stream()
                        .map(section::scaleUpPosition)
                        .collect(Collectors.toList())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public Optional<Position> clickPositionOf(final Supplier<Image> supplier) {
        Ensure.suppliesNotNull(supplier);
        return positionOf(supplier).map(position -> position.addSubPosition(supplier.get().middle()));
    }

    public Optional<Position> clickPositionOf(final Supplier<Image> supplier, final Section section) {
        Ensure.suppliesNotNull(supplier);
        Ensure.notNull(section);
        return positionOf(supplier, section).map(position -> position.addSubPosition(supplier.get().middle()));
    }

    public Optional<Position> clickPositionOf(final List<Supplier<Image>> suppliers) {
        Ensure.notNull(suppliers);
        suppliers.forEach(Ensure::suppliesNotNull);

        return positionOf(suppliers).map(position -> position.addSubPosition(
                suppliers.stream().map(Supplier::get)
                        .filter(image -> imageAt(image, position))
                        .findFirst().map(Image::middle).orElse(new Position(0,0))));
    }



    public Optional<Position> clickPositionOf(final List<Supplier<Image>> suppliers, final Section section) {
        Ensure.notNull(suppliers);
        suppliers.forEach(Ensure::suppliesNotNull);
        Ensure.notNull(section);

        return positionOf(suppliers, section).map(position -> position.addSubPosition(
                suppliers.stream().map(Supplier::get)
                        .filter(image -> imageAt(image, position))
                        .findFirst().map(Image::middle).orElse(new Position(0,0))));
    }


    public List<Position> clickPositionsOf(final Supplier<Image> supplier) {
        Ensure.suppliesNotNull(supplier);
        return positionsOf(supplier).stream()
                .map(position -> position.addSubPosition(supplier.get().middle()))
                .collect(Collectors.toList());
    }

    public List<Position> clickPositionsOf(final Supplier<Image> supplier, final Section section) {
        Ensure.suppliesNotNull(supplier);
        Ensure.notNull(section);

        return positionsOf(supplier, section).stream()
                .map(position -> position.addSubPosition(supplier.get().middle()))
                .collect(Collectors.toList());
    }


    public Map<Image, List<Position>> clickPositionsOf(final Set<Supplier<Image>> suppliers) {
        Ensure.containsNoNull(suppliers);
        suppliers.forEach(Ensure::suppliesNotNull);

        return positionsOf(suppliers).entrySet().stream()
                .peek(entry -> entry.setValue(entry.getValue().stream().map(position -> position
                        .addSubPosition(entry.getKey().middle())).collect(Collectors.toList())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
/*
        Map<Image, List<Position>> result = new HashMap<>();

        for (Map.Entry<Image, List<Position>> entry : positionsOf(suppliers).entrySet()) {
            result.put(entry.getKey(),
                    entry.getValue().stream().map(position -> position
                            .addSubPosition(entry.getKey().middle())).collect(Collectors.toList()));
        }

        return result;
        */
    }


    public Map<Image, List<Position>> clickPositionsOf(final Set<Supplier<Image>> suppliers, final Section section) {
        Ensure.containsNoNull(suppliers);
        suppliers.forEach(Ensure::suppliesNotNull);
        Ensure.notNull(section);

        return positionsOf(suppliers, section).entrySet().stream()
                .peek(entry -> entry.setValue(entry.getValue().stream().map(position -> position
                        .addSubPosition(entry.getKey().middle())).collect(Collectors.toList())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


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

    @Override
    public Image get() {
        return getScreenSupplier().get();
    }
}
