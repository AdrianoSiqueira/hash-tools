package hashtools.gui.screen.about;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    @FXML private GridPane paneRoot;

    @FXML private Hyperlink hyperlinkApplication;
    @FXML private Hyperlink hyperlinkGithub;
    @FXML private Hyperlink hyperlinkLinkedin;

    private Stage stage;

    private HostServices getHostServices() {
        return (HostServices) stage.getProperties().get("host.services");
    }

    @FXML
    private void openLink(ActionEvent event) {
        if (!(event.getSource() instanceof Hyperlink hyperlink)) return;

        String url = hyperlink.getText();
        getHostServices().showDocument(url);
    }

    private void setStage() {
        this.stage = (Stage) paneRoot.getScene().getWindow();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::setStage);
    }
}
