package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.algorithm.find.ImagePositionFinder;
import io.github.mschmidae.guiautomation.algorithm.find.SimpleFinder;
import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.Section;
import io.github.mschmidae.guiautomation.util.helper.Ensure;
import io.github.mschmidae.guiautomation.util.image.Image;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Screen implements Supplier<Image> {
  private final Supplier<Image> screenSupplier;
  private final ImagePositionFinder finder;

  public Screen() {
    this(new AwtScreenshotSupplier());
  }

  public Screen(final Supplier<Image> screenSupplier) {
    this(new SimpleFinder(), screenSupplier);
  }

  /**
   * Constructor which injects the dependencies of a Screen.
   * @param finder instance of a ImagePositionFinder to find a sub image in an image
   * @param screenSupplier supplier of screenshots
   */
  public Screen(final ImagePositionFinder finder, final Supplier<Image> screenSupplier) {
    Ensure.notNull(finder);
    Ensure.notNull(screenSupplier);

    this.finder = finder;
    this.screenSupplier = screenSupplier;
  }

  /**
   * Find the first position of the pattern image on the screen.
   * @param supplier of the pattern image
   * @return first Position of the pattern image or an empty optional if the pattern image isn't
   *         present.
   */
  public Optional<Position> positionOf(final Supplier<Image> supplier) {
    Ensure.suppliesNotNull(supplier);
    Image screen = getScreenSupplier().get();
    return getFinder().find(screen, supplier.get());
  }

  /**
   * Find the first position of the pattern image in the section on the screen.
   * @param supplier of the pattern image
   * @param section which should contain the pattern image
   * @return first Position of the pattern image or an empty optional if the pattern image isn't
   *         present in the section.
   */
  public Optional<Position> positionOf(final Supplier<Image> supplier, final Section section) {
    Ensure.suppliesNotNull(supplier);
    Ensure.notNull(section);

    Image screen = getScreenSupplier().get().getSubImage(section);
    return getFinder().find(screen, supplier.get())
        .map(section::scaleUpPosition);
  }

  /**
   * Find the first Position of the first present pattern image on the screen.
   * @param suppliers list of pattern image suppliers. The order of the list represents the priority
   *                  of the pattern image supplier. The entry with index 0 has the highest
   *                  priority.
   * @return first Position of the pattern image or an empty optional if the pattern image isn't
   *         present.
   */
  public Optional<Position> positionOf(final List<Supplier<Image>> suppliers) {
    Ensure.containsNoNull(suppliers);
    suppliers.forEach(Ensure::suppliesNotNull);

    Image screen = getScreenSupplier().get();
    return suppliers.stream()
        .map(Supplier::get)
        .flatMap(pattern -> getFinder().findAll(screen, pattern).stream())
        .findFirst();
  }

  /**
   * Find the first Position of the first present pattern image in the section on the screen.
   * @param suppliers list of pattern image suppliers. The order of the list represents the priority
   *                  of the pattern image supplier. The entry with index 0 has the highest
   *                  priority.
   * @param section which should contain the pattern image
   * @return first Position of the pattern image or an empty optional if the pattern image isn't
   *         present in the section.
   */
  public Optional<Position> positionOf(final List<Supplier<Image>> suppliers,
                                       final Section section) {
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

  /**
   * Find all positions of the pattern image on the screen.
   * @param supplier of the pattern image
   * @return list of all Positions of the pattern image or an empty list if the pattern image isn't
   *         present.
   */
  public List<Position> positionsOf(final Supplier<Image> supplier) {
    Ensure.suppliesNotNull(supplier);

    Image screen = getScreenSupplier().get();
    return getFinder().findAll(screen, supplier.get());
  }

  /**
   * Find all positions of the pattern image in the section on the screen.
   * @param supplier of the pattern image
   * @param section which should contain the pattern image
   * @return list of all Positions of the pattern image or an empty list if the pattern image isn't
   *         present in the section.
   */
  public List<Position> positionsOf(final Supplier<Image> supplier, final Section section) {
    Ensure.suppliesNotNull(supplier);
    Ensure.notNull(section);

    Image screen = getScreenSupplier().get().getSubImage(section);

    return getFinder().findAll(screen, supplier.get()).stream()
        .map(section::scaleUpPosition)
        .collect(Collectors.toList());
  }

  /**
   * Find all positions of all pattern images on the screen.
   * @param suppliers set of pattern image suppliers
   * @return map of all pattern images as key and the list of positions as value. When the pattern
   *         isn't present the value is an empty list
   */
  public Map<Image, List<Position>> positionsOf(final Set<Supplier<Image>> suppliers) {
    Ensure.containsNoNull(suppliers);
    suppliers.forEach(Ensure::suppliesNotNull);

    Set<Image> images = suppliers.stream().map(Supplier::get).collect(Collectors.toSet());
    Image screen = getScreenSupplier().get();
    return getFinder().findAll(screen, images);
  }

  /**
   * Find all positions of all pattern images in the section on the screen.
   * @param suppliers set of pattern image suppliers
   * @param section which should contain the pattern image
   * @return map of all pattern images as key and the list of positions as value. When the pattern
   *         isn't present in the section the value is an empty list
   */
  public Map<Image, List<Position>> positionsOf(final Set<Supplier<Image>> suppliers,
                                                final Section section) {
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
    return positionOf(supplier, section)
        .map(position -> position.addSubPosition(supplier.get().middle()));
  }

  public Optional<Position> clickPositionOf(final List<Supplier<Image>> suppliers) {
    Ensure.notNull(suppliers);
    suppliers.forEach(Ensure::suppliesNotNull);

    return positionOf(suppliers).map(position -> position.addSubPosition(
        suppliers.stream().map(Supplier::get)
            .filter(image -> imageAt(image, position))
            .findFirst().map(Image::middle).orElse(new Position(0, 0))));
  }


  public Optional<Position> clickPositionOf(final List<Supplier<Image>> suppliers,
                                            final Section section) {
    Ensure.notNull(suppliers);
    suppliers.forEach(Ensure::suppliesNotNull);
    Ensure.notNull(section);

    return positionOf(suppliers, section).map(position -> position.addSubPosition(
        suppliers.stream().map(Supplier::get)
            .filter(image -> imageAt(image, position))
            .findFirst().map(Image::middle).orElse(new Position(0, 0))));
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
  }


  public Map<Image, List<Position>> clickPositionsOf(final Set<Supplier<Image>> suppliers,
                                                     final Section section) {
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
