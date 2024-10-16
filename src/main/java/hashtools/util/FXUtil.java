package hashtools.util;

import javafx.event.Event;
import javafx.scene.Node;

public class FXUtil {

    public static String getFXMLPath(Event event) {
        return (String) FXUtil
            .getNode(event)
            .getUserData();
    }

    public static Node getNode(Event event) {
        return (Node) event.getSource();
    }
}
