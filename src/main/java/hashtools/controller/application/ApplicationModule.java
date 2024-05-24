package hashtools.controller.application;

import hashtools.domain.Condition;

interface ApplicationModule {

    default boolean isNotReadyToRun(Condition<ConditionData> condition, ConditionData data) {
        return condition.isFalse(data);
    }

    void run();
}
