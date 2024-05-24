package hashtools.controller.application;

import hashtools.domain.Condition;
import hashtools.domain.exception.ConditionNotMetException;

import java.nio.file.Files;
import java.nio.file.Path;

class CanRunComparatorCondition implements Condition<ConditionData> {

    @Override
    public boolean isTrue(ConditionData data) {
        if (data.isCheckUseFile1Selected() && !Files.isRegularFile(Path.of(data.getField1Content()))) {
            throw new ConditionNotMetException("The first file is not valid.");
        }

        if (data.isCheckUseFile2Selected() && !Files.isRegularFile(Path.of(data.getField2Content()))) {
            throw new ConditionNotMetException("The second file is not valid.");
        }

        return true;
    }
}
