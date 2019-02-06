package io.github.mschmidae.guiautomation.util.helper;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

class EnsureTest {
    @Test
    void longNotNegative() {
        assertThatThrownBy(() -> Ensure.notNegative(Integer.MIN_VALUE)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Ensure.notNegative(-1)).isInstanceOf(IllegalArgumentException.class);
        Ensure.notNegative(0);
        Ensure.notNegative(1);
        Ensure.notNegative(Integer.MAX_VALUE);
    }

    @Test
    void longGreaterOrEqual() {
        assertThatThrownBy(() -> Ensure.greaterOrEqual(1, 2)).isInstanceOf(IllegalArgumentException.class);
        Ensure.greaterOrEqual(1, 1);
        Ensure.greaterOrEqual(2, 1);
    }

    @Test
    void longGreater() {
        assertThatThrownBy(() -> Ensure.greater(1, 2)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Ensure.greater(1, 1)).isInstanceOf(IllegalArgumentException.class);
        Ensure.greater(2, 1);
    }

    @Test
    void longSmallerOrEqual() {
        Ensure.smallerOrEqual(1, 2);
        Ensure.smallerOrEqual(1, 1);
        assertThatThrownBy(() -> Ensure.smallerOrEqual(2, 1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void longSmaller() {
        Ensure.smaller(1, 2);
        assertThatThrownBy(() -> Ensure.smaller(1, 1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Ensure.smaller(2, 1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void longEqual() {
        Ensure.equal(1, 1);
        assertThatThrownBy(() -> Ensure.equal(1, 2)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Ensure.equal(1, 0)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void objectNotNull() {
        assertThatThrownBy(() -> Ensure.notNull(null)).isInstanceOf(IllegalArgumentException.class);
        Ensure.notNull(new Object());
        Ensure.notNull("test");
    }

    @Test
    void stringNotBlank() {
        assertThatThrownBy(() -> Ensure.notBlank(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Ensure.notBlank("")).isInstanceOf(IllegalArgumentException.class);
        Ensure.notBlank("test");
    }

    @Test
    void collectionContainsNoNull() {
        Ensure.containsNoNull(Arrays.asList("1", "2"));
        assertThatThrownBy(() -> Ensure.containsNoNull((Collection) null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Ensure.containsNoNull(Arrays.asList(null, "1"))).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Ensure.containsNoNull(Arrays.asList("1", null))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void collectionNotEmpty() {
        Ensure.notEmpty(Arrays.asList("1"));
        assertThatThrownBy(() -> Ensure.notEmpty((Collection) null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Ensure.notEmpty(new ArrayList<>())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void objectArrayContainsNoNull() {
        Ensure.containsNoNull(new Object[]{"1", "2"});
        assertThatThrownBy(() -> Ensure.containsNoNull((Object[]) null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Ensure.containsNoNull(new Object[]{null, "1"})).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Ensure.containsNoNull(new Object[]{"1", null})).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void objectArrayIsNotEmpty() {
        Ensure.notEmpty(new Object[1]);
        assertThatThrownBy(() -> Ensure.notEmpty((Object[]) null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Ensure.notEmpty(new Object[0])).isInstanceOf(IllegalArgumentException.class);
    }

}