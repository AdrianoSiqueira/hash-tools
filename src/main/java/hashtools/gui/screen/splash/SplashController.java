package hashtools.gui.screen.splash;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * <p>
 * Splash screen controller class.
 * </p>
 *
 * @author Adriano Siqueira
 */
public class SplashController implements Initializable {

    @FXML private StackPane paneRoot;
    @FXML private ImageView imageView;


    private void bindImageViewSizeToPane() {
        imageView.fitHeightProperty()
                 .bind(paneRoot.heightProperty().multiply(0.99));
        imageView.fitWidthProperty()
                 .bind(paneRoot.widthProperty().multiply(0.99));
    }

    private void setInitialImageViewSize() {
        imageView.setFitHeight(paneRoot.getHeight());
        imageView.setFitWidth(paneRoot.getWidth());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInitialImageViewSize();
        bindImageViewSizeToPane();
    }
}
