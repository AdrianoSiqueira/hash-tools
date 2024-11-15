package hashtools;

import hashtools.application.ApplicationWindow;
import hashtools.shared.Resource;
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
