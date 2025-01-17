package hashtools;

import hashtools.applicationmodule.application.ApplicationWindow;
import hashtools.coremodule.Resource;
import javafx.application.Application;

public class Main {

    private static void enablePreloader() {
        System.setProperty(
            Resource.PropertyKey.JAVAFX_PRELOADER,
            Resource.StaticImplementation.PRELOADER_CLASS
        );
    }

    public static void main(String[] args) {
        enablePreloader();

        Application.launch(ApplicationWindow.class);
    }
}
