package hashtools.controller.application;

import hashtools.dialog.MessageDialog;
import hashtools.domain.Condition;
import javafx.application.Platform;

import java.nio.file.Files;
import java.nio.file.Path;

class CanRunGeneratorCondition implements Condition<ConditionData> {

    @Override
    public boolean isTrue(ConditionData data) {
        if (!Platform.isFxApplicationThread()) {
            throw new IllegalStateException("This class must be called from a JavaFX thread");
        }

        if (data.isCheckUseFile1Selected() && !Files.isRegularFile(Path.of(data.getField1Content()))) {
            MessageDialog
                .builder()
                .title("Application Controller")
                .content("The input file is not valid.")
                .build()
                .configure()
                .showAndWait();
            return false;
        }

        var noAlgorithmSelected = !data.isCheckMD5Selected()
            && !data.isCheckSHA1Selected()
            && !data.isCheckSHA224Selected()
            && !data.isCheckSHA256Selected()
            && !data.isCheckSHA384Selected()
            && !data.isCheckSHA512Selected();

        if (noAlgorithmSelected) {
            MessageDialog
                .builder()
                .title("Application Controller")
                .content("No algorithms selected.")
                .build()
                .configure()
                .showAndWait();
            return false;
        }

        return true;
    }
}
