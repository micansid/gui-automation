package io.github.mschmidae.guiautomation.control;

import io.github.mschmidae.guiautomation.util.helper.Ensure;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.Getter;


public class ActionBuilder {

  public <T> ComposedPreconditionFunction<T> preconditionWithResult(
      final Function<Controller, Optional<T>> precondition) {
    Ensure.notNull(precondition);
    return new ComposedPrecondition<>(precondition);
  }

  public ComposedPreconditionPredicate<Boolean> precondition(
      final Predicate<Controller> precondition) {
    Ensure.notNull(precondition);
    return new ComposedPrecondition<>(controller ->
        precondition.test(controller) ? Optional.of(true) : Optional.empty());
  }

  public ComposedPreconditionPredicate<Boolean> emptyPrecondition() {
    return precondition(controller -> true);
  }

  public <T> ComposedPreconditionFunction<T> emptyPreconditionWithResult(
      final Supplier<T> precondition) {
    Ensure.notNull(precondition);
    return new ComposedPrecondition<>(controller -> Optional.of(precondition.get()));
  }


  @Getter(AccessLevel.PRIVATE)
  public class ComposedPrecondition<T> implements ComposedPreconditionFunction<T>,
      ComposedPreconditionPredicate<T> {

    private final Function<Controller, Optional<T>> precondition;

    private ComposedPrecondition(final Function<Controller, Optional<T>> precondition) {
      this.precondition = precondition;
    }

    @Override
    public <S> ComposedActionFunction<S> actionWithResult(
        final BiFunction<Controller, T, S> action) {
      Ensure.notNull(action);
      return new ComposedAction<>(action);
    }

    @Override
    public <S> ComposedActionFunction<S> actionWithResult(final Function<Controller, S> action) {
      Ensure.notNull(action);
      return new ComposedAction<>((controller, t) -> action.apply(controller));
    }

    @Override
    public ComposedActionConsumer<Object> action(
        final BiConsumer<Controller, T> action) {
      Ensure.notNull(action);
      return new ComposedAction<>((controller, t) -> {
        action.accept(controller, t);
        return new Object();
      });
    }

    @Override
    public ComposedActionConsumer<Object> action(final Consumer<Controller> action) {
      Ensure.notNull(action);
      return new ComposedAction<>((controller, t) -> {
        action.accept(controller);
        return new Object();
      });
    }


    @Getter(AccessLevel.PRIVATE)
    public class ComposedAction<S> implements ComposedActionFunction<S>, ComposedActionConsumer<S> {
      private final BiFunction<Controller, T, S> action;

      private ComposedAction(final BiFunction<Controller, T, S> action) {
        this.action = action;
      }

      @Override
      public <U> Buildable<U> postconditionWithResult(
          final BiFunction<Controller, S, Optional<U>> postcondition) {
        Ensure.notNull(postcondition);
        return new ComposedPostcondition<>(postcondition);
      }

      @Override
      public <U> Buildable<U> postconditionWithResult(final Function<Controller,
          Optional<U>> postcondition) {
        Ensure.notNull(postcondition);
        return new ComposedPostcondition<>(((controller, s) -> postcondition.apply(controller)));
      }

      @Override
      public Buildable<Boolean> postcondition(
          final BiPredicate<Controller, S> postcondition) {
        Ensure.notNull(postcondition);
        return new ComposedPostcondition<>(((controller, s) ->
            postcondition.test(controller, s) ? Optional.of(true) : Optional.empty()));
      }

      @Override
      public Buildable<Boolean> postcondition(
          final Predicate<Controller> postcondition) {
        Ensure.notNull(postcondition);
        return new ComposedPostcondition<>(((controller, s) ->
            postcondition.test(controller) ? Optional.of(true) : Optional.empty()));
      }

      @Override
      public Buildable<Boolean> emptyPostcondition() {
        return new ComposedPostcondition<>((controller, s) -> Optional.of(true));
      }

      @Getter(AccessLevel.PRIVATE)
      public class ComposedPostcondition<U> implements Buildable<U> {
        private final BiFunction<Controller, S, Optional<U>> postcondition;

        private ComposedPostcondition(final BiFunction<Controller, S, Optional<U>> postcondition) {
          this.postcondition = postcondition;
        }

        public Function<Controller, U> build() {
          return controller -> {
            T preTransfer = getPrecondition().apply(controller)
                .orElseThrow(() -> new RuntimeException("The pre condition isn't fulfilled"));
            S actionTransfer = getAction().apply(controller, preTransfer);
            return getPostcondition().apply(controller, actionTransfer)
                .orElseThrow(() -> new RuntimeException("The post condition isn't fulfilled"));
          };
        }
      }
    }
  }

  public interface ComposedPreconditionFunction<T> {
    <S> ComposedActionFunction<S> actionWithResult(final BiFunction<Controller, T, S> action);

    ComposedActionConsumer<Object> action(final BiConsumer<Controller, T> action);
  }

  public interface ComposedPreconditionPredicate<T> {
    <S> ComposedActionFunction<S> actionWithResult(final Function<Controller, S> action);

    ComposedActionConsumer<Object> action(final Consumer<Controller> action);
  }

  public interface ComposedActionFunction<S> {
    <U> Buildable<U> postconditionWithResult(
        final BiFunction<Controller, S, Optional<U>> postcondition);

    Buildable<Boolean> postcondition(
        final BiPredicate<Controller, S> postcondition);

    Buildable<Boolean> emptyPostcondition();
  }

  public interface ComposedActionConsumer<S> {
    <U> Buildable<U> postconditionWithResult(
        final Function<Controller, Optional<U>> postcondition);

    Buildable<Boolean> postcondition(
        final Predicate<Controller> postcondition);

    Buildable<Boolean> emptyPostcondition();
  }

  public interface Buildable<U> {
    Function<Controller, U> build();
  }
}
