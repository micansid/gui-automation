package io.github.mschmidae.guiautomation.control.clipboard;

import io.github.mschmidae.guiautomation.util.helper.Ensure;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public class Clipboard {
  private final Supplier<Optional<String>> getter;
  private final Consumer<String> setter;

  public Clipboard(final Supplier<Optional<String>> getter, final Consumer<String> setter) {
    Ensure.notNull(getter);
    Ensure.notNull(setter);
    this.getter = getter;
    this.setter = setter;
  }

  public Clipboard(final ClipboardCommandExecutor executor) {
    this(executor, executor);
  }

  public Clipboard() {
    this(new AwtClipboardCommandExecutor());
  }

  public void set(final String data) {
    Ensure.notNull(data);
    getSetter().accept(data);
  }

  public Optional<String> get() {
    return getGetter().get();
  }
}
