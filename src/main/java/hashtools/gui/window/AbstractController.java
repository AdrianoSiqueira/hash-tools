package hashtools.gui.window;

import hashtools.core.language.LanguageManager;
import hashtools.core.service.WebService;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public abstract class AbstractController {

    protected String fxmlPath;
    protected String stylesheetPath;

    protected WebService webService;
    protected Logger     logger;

    private void addStylesheetIfPresent(Parent root) {
        Optional.ofNullable(stylesheetPath)
                .map(getClass()::getResource)
                .map(URL::toString)
                .ifPresent(root.getStylesheets()::add);
    }

    public abstract void launch(Stage stage);

    protected void loadFavIcon(Stage stage, String url) {
        Optional.ofNullable(getClass().getResourceAsStream(url))
                .map(Image::new)
                .ifPresent(stage.getIcons()::add);
    }

    protected void loadFxml(AbstractController abstractController) {
        if (fxmlPath == null) {
            throw new IllegalStateException("FXML path was not provided.");
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        loader.setController(abstractController);
        loader.setResources(LanguageManager.getBundle());

        try {
            Parent root = loader.load();
            addStylesheetIfPresent(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void loadLogger(AbstractController abstractController) {
        logger = LoggerFactory.getLogger(abstractController.getClass());
    }

    protected void loadWebService(Stage stage) {
        Optional.ofNullable(stage.getProperties().get("host.services"))
                .map(HostServices.class::cast)
                .map(WebService::new)
                .ifPresent(webService -> this.webService = webService);
    }
}
