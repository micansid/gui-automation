package io.github.mschmidae.guiautomation.control.mouse;

import io.github.mschmidae.guiautomation.control.awt.AwtMouseCommandExecutor;
import io.github.mschmidae.guiautomation.control.awt.AwtMousePositionSupplier;
import io.github.mschmidae.guiautomation.util.Position;
import io.github.mschmidae.guiautomation.util.helper.Ensure;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class Mouse {
  private final MouseCommandExecutor executor;
  private final Supplier<Position> positionSupplier;

  @Getter
  private Position lastMovePosition;

  private final Logger logger = LoggerFactory.getLogger(getClass());


  public Mouse() {
    this(new AwtMouseCommandExecutor(), new AwtMousePositionSupplier());
  }

  /**
   * Constructor which injects the dependencies of a Mouse.
   * @param executor for mouse action
   * @param positionSupplier supplier of current mouse position
   */
  public Mouse(final MouseCommandExecutor executor, final Supplier<Position> positionSupplier) {
    Ensure.notNull(executor);
    Ensure.notNull(positionSupplier);
    this.executor = executor;
    this.positionSupplier = positionSupplier;
    lastMovePosition = new Position(0, 0);
  }


  /**
   * Move mouse position to new position.
   * @param position to move
   * @return this for fluent interface
   */
  public Mouse move(final Position position) {
    Ensure.notNull(position);
    getLogger().debug("move mouse to: " + position);
    getExecutor().move(position.getX(), position.getY());
    setLastMovePosition(position);
    return this;
  }


  /**
   * Move the mouse position left.
   * @param pixel to move left
   * @return this for fluent interface
   */
  public Mouse moveLeft(final int pixel) {
    Ensure.notNegative(pixel);
    return move(new Position(getLastMovePosition().getX() - pixel,
        getLastMovePosition().getY()));
  }


  /**
   * Move the mouse position right.
   * @param pixel to move right
   * @return this for fluent interface
   */
  public Mouse moveRight(final int pixel) {
    Ensure.notNegative(pixel);
    return move(new Position(getLastMovePosition().getX() + pixel,
        getLastMovePosition().getY()));
  }


  /**
   * Move the mouse position up.
   * @param pixel to move up
   * @return this for fluent interface
   */
  public Mouse moveUp(final int pixel) {
    Ensure.notNegative(pixel);
    return move(new Position(getLastMovePosition().getX(),
        getLastMovePosition().getY() - pixel));
  }


  /**
   * Move the mouse position down.
   * @param pixel to move down
   * @return this for fluent interface
   */
  public Mouse moveDown(final int pixel) {
    Ensure.notNegative(pixel);
    return move(new Position(getLastMovePosition().getX(),
        getLastMovePosition().getY() + pixel));
  }


  /**
   * Click a mouse button.
   * @param button to click
   * @return this for fluent interface
   */
  public Mouse click(final MouseButton button) {
    Ensure.notNull(button);
    getLogger().debug("click button: " + button);
    getExecutor().press(button);
    getExecutor().release(button);
    return this;
  }


  /**
   * Click the left mouse button.
   * @return this for fluent interface
   */
  public Mouse leftClick() {
    return click(MouseButton.LEFT);
  }


  /**
   * Double click the left mouse button.
   * @return this for fluent interface
   */
  public Mouse leftDoubleClick() {
    return leftClick().leftClick();
  }


  /**
   * Click the middle mouse button.
   * @return this for fluent interface
   */
  public Mouse middleClick() {
    return click(MouseButton.MIDDLE);
  }


  /**
   * Click the right mouse button.
   * @return this for fluent interface
   */
  public Mouse rightClick() {
    return click(MouseButton.RIGHT);
  }


  /**
   * Drag the current position with a left click and release it at the new position.
   * @param position where to drop
   * @return this for fluent interface
   */
  public Mouse dragAndDrop(final Position position) {
    Ensure.notNull(position);
    getLogger().debug("drag and drop from " + getPositionSupplier().get() + " to " + position);
    getExecutor().press(MouseButton.LEFT);
    getExecutor().move(position.getX(), position.getY());
    getExecutor().release(MouseButton.LEFT);
    return this;
  }


  /**
   * Execute a scroll down action.
   * @param notches to scroll down
   * @return this for fluent interface
   */
  public Mouse scrollDown(final int notches) {
    Ensure.notNegative(notches);
    getLogger().debug("scroll down " + notches + " notches");
    getExecutor().scroll(notches);
    return this;
  }


  /**
   * Execute a scroll up action.
   * @param notches to scroll up
   * @return this for fluent interface
   */
  public Mouse scrollUp(final int notches) {
    Ensure.notNegative(notches);
    getLogger().debug("scroll up " + notches + " notches");
    getExecutor().scroll(-notches);
    return this;
  }

  public Position currentPosition() {
    return getPositionSupplier().get();
  }
}
