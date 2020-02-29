package io.github.micansid.guiautomation.control;

import io.github.micansid.guiautomation.util.helper.Ensure;
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

  /**
   * Set the precondition witch returns a result.
   * @param precondition gets a controller an should return a optional result. If the optional is
   *                     set the precondition is fulfilled.
   * @param <T> type of the precondition result
   * @return a ComposedPreconditionFunction object with expects a BiConsumer or a BiFunction to
   *         handle the result of the precondition.
   */
  public <T> ComposedPreconditionFunction<T> preconditionWithResult(
      final Function<Controller, Optional<T>> precondition) {
    Ensure.notNull(precondition);
    return new ComposedPrecondition<>(precondition);
  }

  /**
   * Set the precondition witch has no result.
   * @param precondition gets a controller an decides if the precondition is fulfilled.
   * @return a ComposedPreconditionPredicate object with expects a Consumer or a Function as action.
   */
  public ComposedPreconditionPredicate<Boolean> precondition(
      final Predicate<Controller> precondition) {
    Ensure.notNull(precondition);
    return new ComposedPrecondition<>(controller ->
        precondition.test(controller) ? Optional.of(true) : Optional.empty());
  }

  /**
   * Set a precondition witch is always fulfilled.
   * @return a ComposedPreconditionPredicate object with expects a Consumer or a Function as action.
   */
  public ComposedPreconditionPredicate<Boolean> emptyPrecondition() {
    return precondition(controller -> true);
  }

  /**
   * Set a precondition witch is always fulfilled and returns a parameter to the action.
   * @param parameterSupplier supplies the action parameter
   * @param <T> type of the precondition result
   * @return a ComposedPreconditionFunction object with expects a BiConsumer or a BiFunction to
   *         handle the returned parameter.
   */
  public <T> ComposedPreconditionFunction<T> emptyPreconditionWithResult(
      final Supplier<T> parameterSupplier) {
    Ensure.notNull(parameterSupplier);
    return new ComposedPrecondition<>(controller -> Optional.of(parameterSupplier.get()));
  }


  /**
   * Builder level to set the action with will be executed if the precondition is fulfilled. The
   * implemented interfaces minimize the possible methods according the precondition.
   * @param <T> type of the parameter from the precondition
   */
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

    /**
     * Builder level to set the postcondition to verify the success of the action. The
     * implemented interfaces minimize the possible methods according the action.
     * @param <S> type the result of the postcondition.
     */
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

      @Override
      public Buildable<S> emptyPostconditionWithResult() {
        return new ComposedPostcondition<S>((controller, s) -> Optional.of(s));
      }

      /**
       * Builder level all conditions and the action is composed and the Builder is ready to be
       * built.
       * @param <U> type of the result returned by the postcondition
       */
      @Getter(AccessLevel.PRIVATE)
      public class ComposedPostcondition<U> implements Buildable<U> {
        private final BiFunction<Controller, S, Optional<U>> postcondition;

        private ComposedPostcondition(final BiFunction<Controller, S, Optional<U>> postcondition) {
          this.postcondition = postcondition;
        }

        @Override
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

  /**
   * Returned type when the precondition returns a result.
   * @param <T> type of the result
   */
  public interface ComposedPreconditionFunction<T> {

    /**
     * Set a action witch gets a controller and a parameter from the precondition and returns a
     * result to the postcondition.
     * @param action to execute when precondition is fulfilled.
     * @param <S> type of the action result
     * @return a ComposedActionFunction object with expects a BiConsumer or a BiFunction to
     *         handle the returned parameter in the postcondition.
     */
    <S> ComposedActionFunction<S> actionWithResult(final BiFunction<Controller, T, S> action);

    /**
     * Set a action witch gets a controller and a parameter from the precondition.
     * @param action to execute when precondition is fulfilled.
     * @return a ComposedActionConsumer object with expects a Consumer or a Function to verify the
     *         action.
     */
    ComposedActionConsumer<Object> action(final BiConsumer<Controller, T> action);
  }

  /**
   * Returned type when the precondition doesn't returns a result.
   * @param <T> type of the result
   */
  public interface ComposedPreconditionPredicate<T> {
    /**
     * Set a action witch gets a controller and returns a result to the postcondition.
     * @param action to execute when precondition is fulfilled.
     * @param <S> type of the action result
     * @return a ComposedActionFunction object with expects a BiConsumer or a BiFunction to
     *         handle the returned parameter in the postcondition.
     */
    <S> ComposedActionFunction<S> actionWithResult(final Function<Controller, S> action);

    /**
     * Set a action witch only gets a controller.
     * @param action to execute when precondition is fulfilled.
     * @return a ComposedActionConsumer object with expects a Consumer or a Function to verify the
     *         action.
     */
    ComposedActionConsumer<Object> action(final Consumer<Controller> action);
  }

  /**
   * Returned type when the action returns a result.
   * @param <S> type of the result
   */
  public interface ComposedActionFunction<S> {
    /**
     * Set a postcondition witch gets the result of the action and a controller to verify the
     * success of the action and returns also a result in an optional.
     * @param postcondition to verify the action
     * @param <U> type of the postcondition result
     * @return a Buildable, ready to build the action function.
     */
    <U> Buildable<U> postconditionWithResult(
        final BiFunction<Controller, S, Optional<U>> postcondition);

    /**
     * Set a postcondition witch gets the result of the action and a controller to verify the
     * success of the action.
     * @param postcondition to verify the action
     * @return a Buildable, ready to build the action function.
     */
    Buildable<Boolean> postcondition(
        final BiPredicate<Controller, S> postcondition);

    /**
     * Set an always fulfilled precondition and return the result of the action.
     * @return a Buildable, ready to build the action function.
     */
    Buildable<S> emptyPostconditionWithResult();
  }

  /**
   * Returned type when the action doesn't returns a result.
   * @param <S> type of the result
   */
  public interface ComposedActionConsumer<S> {
    /**
     * Set a postcondition to verify the result of the action.
     * @param postcondition to verify the action and return a result
     * @param <U>
     * @return a Buildable, ready to build the action function.
     */
    <U> Buildable<U> postconditionWithResult(
        final Function<Controller, Optional<U>> postcondition);

    /**
     * Set a postcondition to verify the result of the action.
     * @param postcondition to verify the action
     * @return a Buildable, ready to build the action function.
     */
    Buildable<Boolean> postcondition(
        final Predicate<Controller> postcondition);

    /**
     * Set an always fulfilled precondition.
     * @return a Buildable, ready to build the action function.
     */
    Buildable<Boolean> emptyPostcondition();
  }

  /**
   * Compleatly composed Builder, ready to build.
   * @param <U> type of the result returned by the action function
   */
  public interface Buildable<U> {
    /**
     * Compose precondition, action and postcondition to a single action function.
     * @throws RuntimeException when pre- or postcondition is not fulfilled.
     * @return a function with all composed parts
     */
    Function<Controller, U> build();
  }
}
