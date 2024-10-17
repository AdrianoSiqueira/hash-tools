package hashtools.util;

import javafx.event.Event;
import javafx.scene.Node;

public class FXUtil {

    public static Node getNode(Event event) {
        return (Node) event.getSource();
    }

    public static <T> T getUserData(Event event, Class<T> userDataType) {
        return userDataType.cast(getNode(event));
    }
}
