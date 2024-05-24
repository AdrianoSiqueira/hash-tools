package hashtools.controller.application;

import hashtools.domain.Condition;
import hashtools.domain.exception.ConditionNotMetException;

import java.nio.file.Files;
import java.nio.file.Path;

class CanRunGeneratorCondition implements Condition<ConditionData> {

    @Override
    public boolean isTrue(ConditionData data) {
        if (data.isCheckUseFile1Selected() && !Files.isRegularFile(Path.of(data.getField1Content()))) {
            throw new ConditionNotMetException("The input file is not valid.");
        }

        var noAlgorithmIsSelected = !data.isCheckMD5Selected()
            && !data.isCheckSHA1Selected()
            && !data.isCheckSHA224Selected()
            && !data.isCheckSHA256Selected()
            && !data.isCheckSHA384Selected()
            && !data.isCheckSHA512Selected();

        if (noAlgorithmIsSelected) {
            throw new ConditionNotMetException("No algorithms are selected.");
        }

        return true;
    }
}
