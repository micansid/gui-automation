package io.github.micansid.guiautomation.control.keyboard;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public class CharacterKeyMapping {
  private final Map<Integer, Consumer<Keyboard>> mapping = new HashMap<>();

  public CharacterKeyMapping() {
    mapping.put((int)' ', kb -> kb.input(Key.SPACE)); //32
    mapping.put((int)'+', kb -> kb.input(Key.PLUS)); //43
    mapping.put((int)',', kb -> kb.input(Key.COLON)); //44
    mapping.put((int)'-', kb -> kb.input(Key.MINUS)); //45
    mapping.put((int)'.', kb -> kb.input(Key.PERIOD)); //46
    mapping.put((int)'/', kb -> kb.input(Key.SLASH)); //47
    mapping.put((int)'0', kb -> kb.input(Key.NUM_0)); //48
    mapping.put((int)'1', kb -> kb.input(Key.NUM_1)); //49
    mapping.put((int)'2', kb -> kb.input(Key.NUM_2)); //50
    mapping.put((int)'3', kb -> kb.input(Key.NUM_3)); //51
    mapping.put((int)'4', kb -> kb.input(Key.NUM_4)); //52
    mapping.put((int)'5', kb -> kb.input(Key.NUM_5)); //53
    mapping.put((int)'6', kb -> kb.input(Key.NUM_6)); //54
    mapping.put((int)'7', kb -> kb.input(Key.NUM_7)); //55
    mapping.put((int)'8', kb -> kb.input(Key.NUM_8)); //56
    mapping.put((int)'9', kb -> kb.input(Key.NUM_9)); //57

    mapping.put((int)'A', kb -> kb.press(Key.SHIFT).input(Key.A).release(Key.SHIFT)); //65
    mapping.put((int)'B', kb -> kb.press(Key.SHIFT).input(Key.B).release(Key.SHIFT)); //66
    mapping.put((int)'C', kb -> kb.press(Key.SHIFT).input(Key.C).release(Key.SHIFT)); //67
    mapping.put((int)'D', kb -> kb.press(Key.SHIFT).input(Key.D).release(Key.SHIFT)); //68
    mapping.put((int)'E', kb -> kb.press(Key.SHIFT).input(Key.E).release(Key.SHIFT)); //69
    mapping.put((int)'F', kb -> kb.press(Key.SHIFT).input(Key.F).release(Key.SHIFT)); //70
    mapping.put((int)'G', kb -> kb.press(Key.SHIFT).input(Key.G).release(Key.SHIFT)); //71
    mapping.put((int)'H', kb -> kb.press(Key.SHIFT).input(Key.H).release(Key.SHIFT)); //72
    mapping.put((int)'I', kb -> kb.press(Key.SHIFT).input(Key.I).release(Key.SHIFT)); //73
    mapping.put((int)'J', kb -> kb.press(Key.SHIFT).input(Key.J).release(Key.SHIFT)); //74
    mapping.put((int)'K', kb -> kb.press(Key.SHIFT).input(Key.K).release(Key.SHIFT)); //75
    mapping.put((int)'L', kb -> kb.press(Key.SHIFT).input(Key.L).release(Key.SHIFT)); //76
    mapping.put((int)'M', kb -> kb.press(Key.SHIFT).input(Key.M).release(Key.SHIFT)); //77
    mapping.put((int)'N', kb -> kb.press(Key.SHIFT).input(Key.N).release(Key.SHIFT)); //78
    mapping.put((int)'O', kb -> kb.press(Key.SHIFT).input(Key.O).release(Key.SHIFT)); //79
    mapping.put((int)'P', kb -> kb.press(Key.SHIFT).input(Key.P).release(Key.SHIFT)); //80
    mapping.put((int)'Q', kb -> kb.press(Key.SHIFT).input(Key.Q).release(Key.SHIFT)); //81
    mapping.put((int)'R', kb -> kb.press(Key.SHIFT).input(Key.R).release(Key.SHIFT)); //82
    mapping.put((int)'S', kb -> kb.press(Key.SHIFT).input(Key.S).release(Key.SHIFT)); //83
    mapping.put((int)'T', kb -> kb.press(Key.SHIFT).input(Key.T).release(Key.SHIFT)); //84
    mapping.put((int)'U', kb -> kb.press(Key.SHIFT).input(Key.U).release(Key.SHIFT)); //85
    mapping.put((int)'V', kb -> kb.press(Key.SHIFT).input(Key.V).release(Key.SHIFT)); //86
    mapping.put((int)'W', kb -> kb.press(Key.SHIFT).input(Key.W).release(Key.SHIFT)); //87
    mapping.put((int)'X', kb -> kb.press(Key.SHIFT).input(Key.X).release(Key.SHIFT)); //88
    mapping.put((int)'Y', kb -> kb.press(Key.SHIFT).input(Key.Y).release(Key.SHIFT)); //89
    mapping.put((int)'Z', kb -> kb.press(Key.SHIFT).input(Key.Z).release(Key.SHIFT)); //90

    mapping.put((int)'a', kb -> kb.input(Key.A)); //97
    mapping.put((int)'b', kb -> kb.input(Key.B)); //98
    mapping.put((int)'c', kb -> kb.input(Key.C)); //99
    mapping.put((int)'d', kb -> kb.input(Key.D)); //100
    mapping.put((int)'e', kb -> kb.input(Key.E)); //101
    mapping.put((int)'f', kb -> kb.input(Key.F)); //102
    mapping.put((int)'g', kb -> kb.input(Key.G)); //103
    mapping.put((int)'h', kb -> kb.input(Key.H)); //104
    mapping.put((int)'i', kb -> kb.input(Key.I)); //105
    mapping.put((int)'j', kb -> kb.input(Key.J)); //106
    mapping.put((int)'k', kb -> kb.input(Key.K)); //107
    mapping.put((int)'l', kb -> kb.input(Key.L)); //108
    mapping.put((int)'m', kb -> kb.input(Key.M)); //109
    mapping.put((int)'n', kb -> kb.input(Key.N)); //110
    mapping.put((int)'o', kb -> kb.input(Key.O)); //111
    mapping.put((int)'p', kb -> kb.input(Key.P)); //112
    mapping.put((int)'q', kb -> kb.input(Key.Q)); //113
    mapping.put((int)'r', kb -> kb.input(Key.R)); //114
    mapping.put((int)'s', kb -> kb.input(Key.S)); //115
    mapping.put((int)'t', kb -> kb.input(Key.T)); //116
    mapping.put((int)'u', kb -> kb.input(Key.U)); //117
    mapping.put((int)'v', kb -> kb.input(Key.V)); //118
    mapping.put((int)'w', kb -> kb.input(Key.W)); //119
    mapping.put((int)'x', kb -> kb.input(Key.X)); //120
    mapping.put((int)'y', kb -> kb.input(Key.Y)); //121
    mapping.put((int)'z', kb -> kb.input(Key.Z)); //122

  }

  public Consumer<Keyboard> map(final int character) {
    return getMapping().getOrDefault(character, kb -> {});
  }

  public Map<Integer, Consumer<Keyboard>> getCharacterKeyMapping() {
    return Collections.unmodifiableMap(getMapping());
  }
}
