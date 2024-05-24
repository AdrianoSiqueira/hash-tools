package hashtools.controller.application;

import hashtools.domain.Condition;
import hashtools.domain.exception.ConditionNotMetException;

import java.nio.file.Files;
import java.nio.file.Path;

class CanRunCheckerCondition implements Condition<ConditionData> {

    @Override
    public boolean isTrue(ConditionData data) {
        if (data.isCheckUseFile1Selected() && !Files.isRegularFile(Path.of(data.getField1Content()))) {
            throw new ConditionNotMetException("The input file is not valid");
        }

        if (data.isCheckUseFile2Selected() && !Files.isRegularFile(Path.of(data.getField2Content()))) {
            throw new ConditionNotMetException("The checksum file is not valid");
        }

        return true;
    }
}
