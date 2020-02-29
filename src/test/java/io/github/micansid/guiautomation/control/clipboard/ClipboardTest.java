package io.github.micansid.guiautomation.control.clipboard;

import java.util.Optional;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class ClipboardTest {
  @Test
  void setNullThrowsIllegalArgumentException() {
    ClipboardCommandExecutor executor = mock(ClipboardCommandExecutor.class);
    Clipboard sut = new Clipboard(executor);
    assertThatThrownBy(() -> sut.set(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void setCallsExecutor() {
    ClipboardCommandExecutor executor = mock(ClipboardCommandExecutor.class);
    Clipboard sut = new Clipboard(executor);
    sut.set("test");
    verify(executor, times(1)).accept("test");
    verifyNoMoreInteractions(executor);
  }

  @Test
  void getCallsExecutor() {
    ClipboardCommandExecutor executor = mock(ClipboardCommandExecutor.class);
    when(executor.get()).thenReturn(Optional.of("test"));
    Clipboard sut = new Clipboard(executor);
    assertThat(sut.get()).isPresent().contains("test");
  }

}