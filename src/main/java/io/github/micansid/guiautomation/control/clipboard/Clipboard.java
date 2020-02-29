package io.github.micansid.guiautomation.control.clipboard;

import io.github.micansid.guiautomation.control.awt.AwtClipboardCommandExecutor;
import io.github.micansid.guiautomation.util.helper.Ensure;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter(AccessLevel.PRIVATE)
public class Clipboard {
  private final Supplier<Optional<String>> getter;
  private final Consumer<String> setter;

  private final Logger logger = LoggerFactory.getLogger(getClass());

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
    getLogger().debug("set clipboard content to: " + data);
    getSetter().accept(data);
  }

  public Optional<String> get() {
    Optional<String> result = getGetter().get();
    getLogger().debug("get clipboard content: " + result);
    return result;
  }
}
