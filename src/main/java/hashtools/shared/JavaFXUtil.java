package hashtools.shared;

import javafx.event.Event;
import javafx.scene.Node;

public class JavaFXUtil {

    public static Node getNode(Event event) {
        return (Node) event.getSource();
    }

    public static <T> T getUserData(Event event, Class<T> userDataType) {
        return userDataType.cast(getNode(event).getUserData());
    }
}
