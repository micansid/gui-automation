package io.github.mschmidae.guiautomation.util.function;

@FunctionalInterface
public interface TriFunction<T, U, R, S> {
  T apply(U u, R r, S s);
}