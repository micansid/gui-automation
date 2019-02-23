package io.github.mschmidae.guiautomation.control.keyboard;

import io.github.mschmidae.guiautomation.util.helper.Ensure;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public enum Shortcut implements Consumer<Keyboard> {
  // ALT + ...
  ALT_F4(kb -> kb.press(Key.ALT).input(Key.F4).release(Key.ALT)),
  // CONTROL + ...
  CONTROL_A(kb -> kb.press(Key.CONTROL).input(Key.A).release(Key.CONTROL)),
  CONTROL_C(kb -> kb.press(Key.CONTROL).input(Key.C).release(Key.CONTROL)),
  CONTROL_S(kb -> kb.press(Key.CONTROL).input(Key.S).release(Key.CONTROL)),
  CONTROL_V(kb -> kb.press(Key.CONTROL).input(Key.V).release(Key.CONTROL)),
  CONTROL_X(kb -> kb.press(Key.CONTROL).input(Key.X).release(Key.CONTROL)),
  CONTROL_Z(kb -> kb.press(Key.CONTROL).input(Key.Z).release(Key.CONTROL)),
  //ALIAS
  CLOSE(ALT_F4),
  COPY(CONTROL_C),
  CUT(CONTROL_X),
  MARK_ALL(CONTROL_A),
  PASTE(CONTROL_V),
  SAVE(CONTROL_S),
  UNDO(CONTROL_V);

  private final Consumer<Keyboard> keyOrder;

  Shortcut(final Consumer<Keyboard> keyOrder) {
    Ensure.notNull(keyOrder);
    this.keyOrder = keyOrder;
  }

  @Override
  public void accept(Keyboard keyboard) {
    Ensure.notNull(keyboard);
    getKeyOrder().accept(keyboard);
  }
}
