package io.github.mschmidae.guiautomation.control.clipboard;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ClipboardCommandExecutor extends Supplier<Optional<String>>, Consumer<String> {
}
