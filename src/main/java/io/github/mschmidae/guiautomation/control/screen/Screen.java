package io.github.mschmidae.guiautomation.control.screen;

import io.github.mschmidae.guiautomation.algorithm.find.ImagePositionFinder;
import io.github.mschmidae.guiautomation.algorithm.find.SimpleFinder;
import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.Section;
import io.github.mschmidae.guiautomation.util.helper.Ensure;
import io.github.mschmidae.guiautomation.util.image.Image;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Screen implements Supplier<Image> {
  private final Supplier<Image> screenSupplier;
  private final ImagePositionFinder finder;


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
   * @return first position of the pattern image or an empty optional if the pattern image isn't
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
   * @return first position of the pattern image or an empty optional if the pattern image isn't
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
   * Find the first position of the first present pattern image on the screen.
   * @param suppliers list of pattern image suppliers. The order of the list represents the priority
   *                  of the pattern image supplier. The entry with index 0 has the highest
   *                  priority.
   * @return first position of the pattern image or an empty optional if the pattern image isn't
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
   * Find the first position of the first present pattern image in the section on the screen.
   * @param suppliers list of pattern image suppliers. The order of the list represents the priority
   *                  of the pattern image supplier. The entry with index 0 has the highest
   *                  priority.
   * @param section which should contain the pattern image
   * @return first position of the pattern image or an empty optional if the pattern image isn't
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
   * @return list of all positions of the pattern image or an empty list if the pattern image isn't
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
   * @return list of all positions of the pattern image or an empty list if the pattern image isn't
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
  public Map<Image, List<Position>> positionsOf(final Collection<Supplier<Image>> suppliers) {
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
  public Map<Image, List<Position>> positionsOf(final Collection<Supplier<Image>> suppliers,
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


  /**
   * Find the first click position of the pattern image on the screen.
   * @param supplier of the pattern image
   * @return first click position of the pattern image or an empty optional if the pattern image
   *         isn't present.
   */
  public Optional<Position> clickPositionOf(final Supplier<Image> supplier) {
    Ensure.suppliesNotNull(supplier);
    return positionOf(supplier).map(position -> position.addSubPosition(supplier.get().middle()));
  }


  /**
   * Find the first click position of the pattern image in the section on the screen.
   * @param supplier of the pattern image
   * @param section which should contain the pattern image
   * @return first click position of the pattern image or an empty optional if the pattern image
   *         isn't present in the section.
   */
  public Optional<Position> clickPositionOf(final Supplier<Image> supplier, final Section section) {
    Ensure.suppliesNotNull(supplier);
    Ensure.notNull(section);
    return positionOf(supplier, section)
        .map(position -> position.addSubPosition(supplier.get().middle()));
  }


  /**
   * Find the first click position of the first present pattern image on the screen.
   * @param suppliers list of pattern image suppliers. The order of the list represents the priority
   *                  of the pattern image supplier. The entry with index 0 has the highest
   *                  priority.
   * @return first click position of the pattern image or an empty optional if the pattern image
   *         isn't present.
   */
  public Optional<Position> clickPositionOf(final List<Supplier<Image>> suppliers) {
    Ensure.notNull(suppliers);
    suppliers.forEach(Ensure::suppliesNotNull);

    return positionOf(suppliers).map(position -> position.addSubPosition(
        suppliers.stream().map(Supplier::get)
            .filter(image -> imageAt(image, position))
            .findFirst().map(Image::middle).orElse(new Position(0, 0))));
  }


  /**
   * Find the first click position of the first present pattern image in the section on the screen.
   * @param suppliers list of pattern image suppliers. The order of the list represents the priority
   *                  of the pattern image supplier. The entry with index 0 has the highest
   *                  priority.
   * @param section which should contain the pattern image
   * @return first click position of the pattern image or an empty optional if the pattern image
   *         isn't present in the section.
   */
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


  /**
   * Find all click positions of the pattern image on the screen.
   * @param supplier of the pattern image
   * @return list of all click positions of the pattern image or an empty list if the pattern image
   *         isn't present.
   */
  public List<Position> clickPositionsOf(final Supplier<Image> supplier) {
    Ensure.suppliesNotNull(supplier);
    return positionsOf(supplier).stream()
        .map(position -> position.addSubPosition(supplier.get().middle()))
        .collect(Collectors.toList());
  }


  /**
   * Find all click positions of the pattern image in the section on the screen.
   * @param supplier of the pattern image
   * @param section which should contain the pattern image
   * @return list of all click positions of the pattern image or an empty list if the pattern image
   *         isn't present in the section.
   */
  public List<Position> clickPositionsOf(final Supplier<Image> supplier, final Section section) {
    Ensure.suppliesNotNull(supplier);
    Ensure.notNull(section);

    return positionsOf(supplier, section).stream()
        .map(position -> position.addSubPosition(supplier.get().middle()))
        .collect(Collectors.toList());
  }


  /**
   * Find all click positions of all pattern images on the screen.
   * @param suppliers set of pattern image suppliers
   * @return map of all pattern images as key and the list of click positions as value. When the
   *         pattern isn't present the value is an empty list
   */
  public Map<Image, List<Position>> clickPositionsOf(final Collection<Supplier<Image>> suppliers) {
    Ensure.containsNoNull(suppliers);
    suppliers.forEach(Ensure::suppliesNotNull);

    return positionsOf(suppliers).entrySet().stream()
        .peek(entry -> entry.setValue(entry.getValue().stream().map(position -> position
            .addSubPosition(entry.getKey().middle())).collect(Collectors.toList())))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }


  /**
   * Find all click positions of all pattern images in the section on the screen.
   * @param suppliers set of pattern image suppliers
   * @param section which should contain the pattern image
   * @return map of all pattern images as key and the list of click positions as value. When the
   *         pattern isn't present in the section the value is an empty list
   */
  public Map<Image, List<Position>> clickPositionsOf(final Collection<Supplier<Image>> suppliers,
                                                     final Section section) {
    Ensure.containsNoNull(suppliers);
    suppliers.forEach(Ensure::suppliesNotNull);
    Ensure.notNull(section);

    return positionsOf(suppliers, section).entrySet().stream()
        .peek(entry -> entry.setValue(entry.getValue().stream().map(position -> position
            .addSubPosition(entry.getKey().middle())).collect(Collectors.toList())))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }


  /**
   * Verify if the pattern image is present at this position on the screen.
   * @param supplier of the pattern image
   * @param position where the pattern image should be
   * @return true if the pattern image is at this position, else false
   */
  public boolean imageAt(final Supplier<Image> supplier, final Position position) {
    Ensure.suppliesNotNull(supplier);
    Ensure.notNull(position);

    Image screen = getScreenSupplier().get();
    return getFinder().at(screen, supplier.get(), position);
  }

  /**
   * Verify if the key image is present at least one of the positions.
   * @param imagesWithPositions map of images and positions to verify
   * @return result of the verification for each image
   */
  public Map<Image, Boolean> imagesAtOnePosition(
      final Map<Image, List<Position>> imagesWithPositions) {
    return imagesAt(imagesWithPositions, (a, b) -> a | b);
  }

  /**
   * Verify if the key image is present at all positions.
   * @param imagesWithPositions map of images and positions to verify
   * @return result of the verification for each image
   */
  public Map<Image, Boolean> imagesAtAllPositions(
      final Map<Image, List<Position>> imagesWithPositions) {
    return imagesAt(imagesWithPositions, (a, b) -> a & b);
  }

  /**
   * Check each image and all positions if they are still present there.
   * @param imagesWithPositions map of images and positions to verify
   * @param booleanReduce rule to reduce the single result to one result
   * @return result of the verification for each image
   */
  private Map<Image, Boolean> imagesAt(final Map<Image, List<Position>> imagesWithPositions,
                                       final BinaryOperator<Boolean> booleanReduce) {
    Ensure.notNull(imagesWithPositions);

    Image screen = getScreenSupplier().get();
    return imagesWithPositions.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey,
            entry -> entry.getValue().stream()
                .map(position -> getFinder().at(screen, entry.getKey(), position))
                .reduce(booleanReduce)
                .orElse(false)));
  }


  /**
   * Width of the screen.
   * @return width of the screen in pixel
   */
  public int width() {
    return getScreenSupplier().get().getWidth();
  }


  /**
   * Height of the screen.
   * @return height of the screen in pixel
   */
  public int height() {
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
