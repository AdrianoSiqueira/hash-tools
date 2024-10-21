package hashtools.condition;

import javafx.application.Platform;

public class ThreadIsNotJavaFx implements Condition {

    @Override
    public boolean isTrue() {
        return !Platform.isFxApplicationThread();
    }
}
