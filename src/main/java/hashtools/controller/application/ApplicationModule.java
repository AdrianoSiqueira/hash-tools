package hashtools.controller.application;

import hashtools.domain.Condition;
import javafx.application.Platform;

import java.util.concurrent.atomic.AtomicBoolean;

interface ApplicationModule {

    default boolean isNotReadyToRun(Condition<ConditionData> condition, ConditionData data) {
        var hasProblem = new AtomicBoolean();

        Platform.runLater(() -> hasProblem.set(condition.isFalse(data)));

        return hasProblem.get();
    }

    void run();
}
