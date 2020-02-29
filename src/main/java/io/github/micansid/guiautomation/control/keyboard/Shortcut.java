package io.github.micansid.guiautomation.control.keyboard;

import io.github.micansid.guiautomation.util.helper.Ensure;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public enum Shortcut implements Consumer<Keyboard> {
  // ALT + ...
  ALT_F4(kb -> kb.press(Key.ALT).input(Key.F4).release(Key.ALT)),
  ALT_TAB(kb -> kb.press(Key.ALT).input(Key.TAB).release(Key.ALT)),

  ALT_A(kb -> kb.press(Key.ALT).input(Key.A).release(Key.ALT)),
  ALT_B(kb -> kb.press(Key.ALT).input(Key.B).release(Key.ALT)),
  ALT_C(kb -> kb.press(Key.ALT).input(Key.C).release(Key.ALT)),
  ALT_D(kb -> kb.press(Key.ALT).input(Key.D).release(Key.ALT)),
  ALT_E(kb -> kb.press(Key.ALT).input(Key.E).release(Key.ALT)),
  ALT_F(kb -> kb.press(Key.ALT).input(Key.F).release(Key.ALT)),
  ALT_G(kb -> kb.press(Key.ALT).input(Key.G).release(Key.ALT)),
  ALT_H(kb -> kb.press(Key.ALT).input(Key.H).release(Key.ALT)),
  ALT_I(kb -> kb.press(Key.ALT).input(Key.I).release(Key.ALT)),
  ALT_J(kb -> kb.press(Key.ALT).input(Key.J).release(Key.ALT)),
  ALT_K(kb -> kb.press(Key.ALT).input(Key.K).release(Key.ALT)),
  ALT_L(kb -> kb.press(Key.ALT).input(Key.L).release(Key.ALT)),
  ALT_M(kb -> kb.press(Key.ALT).input(Key.M).release(Key.ALT)),
  ALT_N(kb -> kb.press(Key.ALT).input(Key.N).release(Key.ALT)),
  ALT_O(kb -> kb.press(Key.ALT).input(Key.O).release(Key.ALT)),
  ALT_P(kb -> kb.press(Key.ALT).input(Key.P).release(Key.ALT)),
  ALT_Q(kb -> kb.press(Key.ALT).input(Key.Q).release(Key.ALT)),
  ALT_R(kb -> kb.press(Key.ALT).input(Key.R).release(Key.ALT)),
  ALT_S(kb -> kb.press(Key.ALT).input(Key.S).release(Key.ALT)),
  ALT_T(kb -> kb.press(Key.ALT).input(Key.T).release(Key.ALT)),
  ALT_U(kb -> kb.press(Key.ALT).input(Key.U).release(Key.ALT)),
  ALT_V(kb -> kb.press(Key.ALT).input(Key.V).release(Key.ALT)),
  ALT_W(kb -> kb.press(Key.ALT).input(Key.W).release(Key.ALT)),
  ALT_X(kb -> kb.press(Key.ALT).input(Key.X).release(Key.ALT)),
  ALT_Y(kb -> kb.press(Key.ALT).input(Key.Y).release(Key.ALT)),
  ALT_Z(kb -> kb.press(Key.ALT).input(Key.Z).release(Key.ALT)),
  // CONTROL + ...
  CONTROL_A(kb -> kb.press(Key.CONTROL).input(Key.A).release(Key.CONTROL)),
  CONTROL_B(kb -> kb.press(Key.CONTROL).input(Key.B).release(Key.CONTROL)),
  CONTROL_C(kb -> kb.press(Key.CONTROL).input(Key.C).release(Key.CONTROL)),
  CONTROL_D(kb -> kb.press(Key.CONTROL).input(Key.D).release(Key.CONTROL)),
  CONTROL_E(kb -> kb.press(Key.CONTROL).input(Key.E).release(Key.CONTROL)),
  CONTROL_F(kb -> kb.press(Key.CONTROL).input(Key.F).release(Key.CONTROL)),
  CONTROL_G(kb -> kb.press(Key.CONTROL).input(Key.G).release(Key.CONTROL)),
  CONTROL_H(kb -> kb.press(Key.CONTROL).input(Key.H).release(Key.CONTROL)),
  CONTROL_I(kb -> kb.press(Key.CONTROL).input(Key.I).release(Key.CONTROL)),
  CONTROL_J(kb -> kb.press(Key.CONTROL).input(Key.J).release(Key.CONTROL)),
  CONTROL_K(kb -> kb.press(Key.CONTROL).input(Key.K).release(Key.CONTROL)),
  CONTROL_L(kb -> kb.press(Key.CONTROL).input(Key.L).release(Key.CONTROL)),
  CONTROL_M(kb -> kb.press(Key.CONTROL).input(Key.M).release(Key.CONTROL)),
  CONTROL_N(kb -> kb.press(Key.CONTROL).input(Key.N).release(Key.CONTROL)),
  CONTROL_O(kb -> kb.press(Key.CONTROL).input(Key.O).release(Key.CONTROL)),
  CONTROL_P(kb -> kb.press(Key.CONTROL).input(Key.P).release(Key.CONTROL)),
  CONTROL_Q(kb -> kb.press(Key.CONTROL).input(Key.Q).release(Key.CONTROL)),
  CONTROL_R(kb -> kb.press(Key.CONTROL).input(Key.R).release(Key.CONTROL)),
  CONTROL_S(kb -> kb.press(Key.CONTROL).input(Key.S).release(Key.CONTROL)),
  CONTROL_T(kb -> kb.press(Key.CONTROL).input(Key.T).release(Key.CONTROL)),
  CONTROL_U(kb -> kb.press(Key.CONTROL).input(Key.U).release(Key.CONTROL)),
  CONTROL_V(kb -> kb.press(Key.CONTROL).input(Key.V).release(Key.CONTROL)),
  CONTROL_W(kb -> kb.press(Key.CONTROL).input(Key.W).release(Key.CONTROL)),
  CONTROL_X(kb -> kb.press(Key.CONTROL).input(Key.X).release(Key.CONTROL)),
  CONTROL_Y(kb -> kb.press(Key.CONTROL).input(Key.Y).release(Key.CONTROL)),
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
