package io.github.mschmidae.guiautomation.control.keyboard;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public class CharacterKeyMapping {
  private final Map<Character, Consumer<Keyboard>> mapping = new HashMap<>();

  public CharacterKeyMapping() {
    mapping.put('0', kb -> kb.input(Key.NUM_0)); //48
    mapping.put('1', kb -> kb.input(Key.NUM_1)); //49
    mapping.put('2', kb -> kb.input(Key.NUM_2)); //50
    mapping.put('3', kb -> kb.input(Key.NUM_3)); //51
    mapping.put('4', kb -> kb.input(Key.NUM_4)); //52
    mapping.put('5', kb -> kb.input(Key.NUM_5)); //53
    mapping.put('6', kb -> kb.input(Key.NUM_6)); //54
    mapping.put('7', kb -> kb.input(Key.NUM_7)); //55
    mapping.put('8', kb -> kb.input(Key.NUM_8)); //56
    mapping.put('9', kb -> kb.input(Key.NUM_9)); //57

    mapping.put('A', kb -> kb.press(Key.SHIFT).input(Key.A).release(Key.SHIFT)); //65
    mapping.put('B', kb -> kb.press(Key.SHIFT).input(Key.B).release(Key.SHIFT)); //66
    mapping.put('C', kb -> kb.press(Key.SHIFT).input(Key.C).release(Key.SHIFT)); //67
    mapping.put('D', kb -> kb.press(Key.SHIFT).input(Key.D).release(Key.SHIFT)); //68
    mapping.put('E', kb -> kb.press(Key.SHIFT).input(Key.E).release(Key.SHIFT)); //69
    mapping.put('F', kb -> kb.press(Key.SHIFT).input(Key.F).release(Key.SHIFT)); //70
    mapping.put('G', kb -> kb.press(Key.SHIFT).input(Key.G).release(Key.SHIFT)); //71
    mapping.put('H', kb -> kb.press(Key.SHIFT).input(Key.H).release(Key.SHIFT)); //72
    mapping.put('I', kb -> kb.press(Key.SHIFT).input(Key.I).release(Key.SHIFT)); //73
    mapping.put('J', kb -> kb.press(Key.SHIFT).input(Key.J).release(Key.SHIFT)); //74
    mapping.put('K', kb -> kb.press(Key.SHIFT).input(Key.K).release(Key.SHIFT)); //75
    mapping.put('L', kb -> kb.press(Key.SHIFT).input(Key.L).release(Key.SHIFT)); //76
    mapping.put('M', kb -> kb.press(Key.SHIFT).input(Key.M).release(Key.SHIFT)); //77
    mapping.put('N', kb -> kb.press(Key.SHIFT).input(Key.N).release(Key.SHIFT)); //78
    mapping.put('O', kb -> kb.press(Key.SHIFT).input(Key.O).release(Key.SHIFT)); //79
    mapping.put('P', kb -> kb.press(Key.SHIFT).input(Key.P).release(Key.SHIFT)); //80
    mapping.put('Q', kb -> kb.press(Key.SHIFT).input(Key.Q).release(Key.SHIFT)); //81
    mapping.put('R', kb -> kb.press(Key.SHIFT).input(Key.R).release(Key.SHIFT)); //82
    mapping.put('S', kb -> kb.press(Key.SHIFT).input(Key.S).release(Key.SHIFT)); //83
    mapping.put('T', kb -> kb.press(Key.SHIFT).input(Key.T).release(Key.SHIFT)); //84
    mapping.put('U', kb -> kb.press(Key.SHIFT).input(Key.U).release(Key.SHIFT)); //85
    mapping.put('V', kb -> kb.press(Key.SHIFT).input(Key.V).release(Key.SHIFT)); //86
    mapping.put('W', kb -> kb.press(Key.SHIFT).input(Key.W).release(Key.SHIFT)); //87
    mapping.put('X', kb -> kb.press(Key.SHIFT).input(Key.X).release(Key.SHIFT)); //88
    mapping.put('Y', kb -> kb.press(Key.SHIFT).input(Key.Y).release(Key.SHIFT)); //89
    mapping.put('Z', kb -> kb.press(Key.SHIFT).input(Key.Z).release(Key.SHIFT)); //90

    mapping.put('a', kb -> kb.input(Key.A)); //97
    mapping.put('b', kb -> kb.input(Key.B)); //98
    mapping.put('c', kb -> kb.input(Key.C)); //99
    mapping.put('d', kb -> kb.input(Key.D)); //100
    mapping.put('e', kb -> kb.input(Key.E)); //101
    mapping.put('f', kb -> kb.input(Key.F)); //102
    mapping.put('g', kb -> kb.input(Key.G)); //103
    mapping.put('h', kb -> kb.input(Key.H)); //104
    mapping.put('i', kb -> kb.input(Key.I)); //105
    mapping.put('j', kb -> kb.input(Key.J)); //106
    mapping.put('k', kb -> kb.input(Key.K)); //107
    mapping.put('l', kb -> kb.input(Key.L)); //108
    mapping.put('m', kb -> kb.input(Key.M)); //109
    mapping.put('n', kb -> kb.input(Key.N)); //110
    mapping.put('o', kb -> kb.input(Key.O)); //111
    mapping.put('p', kb -> kb.input(Key.P)); //112
    mapping.put('q', kb -> kb.input(Key.Q)); //113
    mapping.put('r', kb -> kb.input(Key.R)); //114
    mapping.put('s', kb -> kb.input(Key.S)); //115
    mapping.put('t', kb -> kb.input(Key.T)); //116
    mapping.put('u', kb -> kb.input(Key.U)); //117
    mapping.put('v', kb -> kb.input(Key.V)); //118
    mapping.put('w', kb -> kb.input(Key.W)); //119
    mapping.put('x', kb -> kb.input(Key.X)); //120
    mapping.put('y', kb -> kb.input(Key.Y)); //121
    mapping.put('z', kb -> kb.input(Key.Z)); //122

  }

  public Consumer<Keyboard> map(final char character) {
    return getMapping().getOrDefault(character, kb -> {});
  }

  public Map<Character, Consumer<Keyboard>> getCharacterKeyMapping() {
    return Collections.unmodifiableMap(getMapping());
  }

  public static void main(String[] args) {
    System.out.println((int)'0');
    System.out.println((int)'9');
  }
}
