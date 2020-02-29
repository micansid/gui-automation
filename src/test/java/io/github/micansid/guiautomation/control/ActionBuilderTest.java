package io.github.micansid.guiautomation.control;

import java.util.Optional;
import java.util.function.Function;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class ActionBuilderTest {
  @Test
  void buildActionWithCommunicationBetweenPreConditionActionAndPostCondition() {
    Object communicationObject = new Object();
    Function<Controller, Object> action = new ActionBuilder()
        .preconditionWithResult(controller -> Optional.of(communicationObject))
        .actionWithResult((controller, object) -> object)
        .postconditionWithResult((controller, object) -> Optional.of(object))
        .build();

    Object result = action.apply(mock(Controller.class));
    assertThat(result).isEqualTo(communicationObject);
  }

  @Test
  void buildActionPredicatePreConditionWithCommunicationBetweenActionAndPostCondition() {
    Object communicationObject = new Object();
    Function<Controller, Object> action = new ActionBuilder()
        .precondition(controller -> true)
        .actionWithResult(controller -> communicationObject)
        .postconditionWithResult((controller, object) -> Optional.of(object))
        .build();

    Object result = action.apply(mock(Controller.class));
    assertThat(result).isEqualTo(communicationObject);
  }

  @Test
  void buildActionEmptyPreConditionWithCommunicationBetweenActionAndPostCondition() {
    Object communicationObject = new Object();
    Function<Controller, Object> action = new ActionBuilder()
        .emptyPrecondition()
        .actionWithResult(controller -> communicationObject)
        .postconditionWithResult((controller, object) -> Optional.of(object))
        .build();

    Object result = action.apply(mock(Controller.class));
    assertThat(result).isEqualTo(communicationObject);
  }

  @Test
  void buildActionSupplierPreConditionWithCommunicationBetweenActionAndPostCondition() {
    Object communicationObject = new Object();
    Function<Controller, Object> action = new ActionBuilder()
        .emptyPreconditionWithResult(() -> communicationObject)
        .actionWithResult((controller, object) -> object)
        .postconditionWithResult((controller, object) -> Optional.of(object))
        .build();

    Object result = action.apply(mock(Controller.class));
    assertThat(result).isEqualTo(communicationObject);
  }

  @Test
  void buildActionWithCommunicationBetweenPreConditionAndAction() {
    Object communicationObject = new Object();
    Function<Controller, Object> action = new ActionBuilder()
        .preconditionWithResult(controller -> Optional.of(communicationObject))
        .action((controller, object) -> {
          if (object != communicationObject) {
            throw new RuntimeException("Communication failed");
          }
        })
        .postconditionWithResult(controller -> Optional.of(communicationObject))
        .build();

    Object result = action.apply(mock(Controller.class));
    assertThat(result).isEqualTo(communicationObject);
  }

  @Test
  void buildActionPredicatePreConditionWithoutCommunicationBetweenActionAndPostCondition() {
    Object communicationObject = new Object();
    Function<Controller, Object> action = new ActionBuilder()
        .precondition(controller -> true)
        .action(controller -> {
        })
        .postconditionWithResult(controller -> Optional.of(communicationObject))
        .build();

    Object result = action.apply(mock(Controller.class));
    assertThat(result).isEqualTo(communicationObject);
  }

  @Test
  void buildActionEmptyPreConditionWithoutCommunicationBetweenActionAndPostCondition() {
    Object communicationObject = new Object();
    Function<Controller, Object> action = new ActionBuilder()
        .emptyPrecondition()
        .action(controller -> {
        })
        .postconditionWithResult(controller -> Optional.of(communicationObject))
        .build();

    Object result = action.apply(mock(Controller.class));
    assertThat(result).isEqualTo(communicationObject);
  }

  @Test
  void buildActionSupplierPreConditionWithoutCommunicationBetweenActionAndPostCondition() {
    Object communicationObject = new Object();
    Function<Controller, Object> action = new ActionBuilder()
        .emptyPreconditionWithResult(() -> communicationObject)
        .action((controller, object) -> {
          if (object != communicationObject) {
            throw new RuntimeException("Communication failed");
          }
        })
        .postconditionWithResult(controller -> Optional.of(communicationObject))
        .build();

    Object result = action.apply(mock(Controller.class));
    assertThat(result).isEqualTo(communicationObject);
  }

  @Test
  void buildActionEmptyPostConditionWithoutCommunicationBetweenActionAndPostCondition() {
    Function<Controller, Boolean> action = new ActionBuilder()
        .emptyPrecondition()
        .action(controller -> {
        })
        .emptyPostcondition()
        .build();

    Boolean result = action.apply(mock(Controller.class));
    assertThat(result).isTrue();
  }

  @Test
  void buildActionPredicatePostConditionWithoutCommunicationBetweenActionAndPostCondition() {
    Function<Controller, Boolean> action = new ActionBuilder()
        .emptyPrecondition()
        .action(controller -> {
        })
        .postcondition(controller -> true)
        .build();

    Boolean result = action.apply(mock(Controller.class));
    assertThat(result).isTrue();
  }

  @Test
  void buildActionPredicatePostConditionWithCommunicationBetweenActionAndPostCondition() {
    Object communicationObject = new Object();
    Function<Controller, Boolean> action = new ActionBuilder()
        .emptyPrecondition()
        .actionWithResult(controller -> communicationObject)
        .postcondition((controller, s) -> true)
        .build();

    Boolean result = action.apply(mock(Controller.class));
    assertThat(result).isTrue();
  }

  @Test
  void buildActionEmptyPostConditionWithCommunicationBetweenActionAndPostCondition() {
    Object communicationObject = new Object();
    Function<Controller, Object> action = new ActionBuilder()
        .emptyPrecondition()
        .actionWithResult(controller -> communicationObject)
        .emptyPostconditionWithResult()
        .build();

    Object result = action.apply(mock(Controller.class));
    assertThat(result).isEqualTo(communicationObject);
  }

  @Test
  void actionFailOnFalsePredicatePreCondition() {
    Function<Controller, Boolean> action = new ActionBuilder()
        .precondition(controller -> false)
        .action(controller -> {
        })
        .emptyPostcondition()
        .build();

    assertThatThrownBy(() -> action.apply(mock(Controller.class)))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("pre condition");
  }

  @Test
  void actionFailOnEmptyOptionalPreCondition() {
    Function<Controller, Boolean> action = new ActionBuilder()
        .preconditionWithResult(controller -> Optional.empty())
        .action((controller, o) -> {
        })
        .emptyPostcondition()
        .build();

    assertThatThrownBy(() -> action.apply(mock(Controller.class)))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("pre condition");
  }

  @Test
  void actionFailOnFalsePredicatePostCondition() {
    Function<Controller, Boolean> action = new ActionBuilder()
        .emptyPrecondition()
        .action(controller -> {
        })
        .postcondition(controller -> false)
        .build();

    assertThatThrownBy(() -> action.apply(mock(Controller.class)))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("post condition");
  }

  @Test
  void actionFailOnFalsePredicatePostConditionCommunicationBetweenActionAndPostCondition() {
    Function<Controller, Boolean> action = new ActionBuilder()
        .emptyPrecondition()
        .actionWithResult(controller -> new Object())
        .postcondition((controller, o) -> false)
        .build();

    assertThatThrownBy(() -> action.apply(mock(Controller.class)))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("post condition");
  }

  @Test
  void actionFailOnEmptyOptionalPostCondition() {
    Function<Controller, Object> action = new ActionBuilder()
        .emptyPrecondition()
        .action(controller -> {
        })
        .postconditionWithResult(controller -> Optional.empty())
        .build();

    assertThatThrownBy(() -> action.apply(mock(Controller.class)))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("post condition");
  }
}