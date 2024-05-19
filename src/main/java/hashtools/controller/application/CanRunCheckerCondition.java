package hashtools.controller.application;

import hashtools.dialog.MessageDialog;
import hashtools.domain.Condition;
import javafx.application.Platform;

import java.nio.file.Files;
import java.nio.file.Path;

class CanRunCheckerCondition implements Condition<ConditionData> {

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

        if (data.isCheckUseFile2Selected() && !Files.isRegularFile(Path.of(data.getField2Content()))) {
            MessageDialog
                .builder()
                .title("Application Controller")
                .content("The checksum file is not valid.")
                .build()
                .configure()
                .showAndWait();
            return false;
        }

        return true;
    }
}
