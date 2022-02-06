package hashtools.gui.screen.manual;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * <p>
 * Manual screen controller class.
 * </p>
 *
 * @author Adriano Siqueira
 */
public class ManualController implements Initializable {

    @FXML private VBox       paneSections;
    @FXML private ScrollPane scrollPaneSections;


    private void bindPaneSectionsHeightWithScrollPane() {
        /*
         * If the 'fitToHeight' property is set to true, the vertical scroll
         * bar does not appear. However, if it is set to false, the panel does
         * not fit correctly.
         *
         * To work around this issue, we bind the preferred height of the panel
         * to the current height of the ScrollPane.
         *
         * It is necessary to make a correction of 2 pixels to prevent the
         * scroll bar from being always visible.
         */
        paneSections.prefHeightProperty()
                    .bind(scrollPaneSections.heightProperty().subtract(2));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindPaneSectionsHeightWithScrollPane();
    }
}
