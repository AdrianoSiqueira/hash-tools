package hashtools.core.runner;

import hashtools.gui.window.application.ApplicationWindow;
import hashtools.gui.window.preloader.PreloaderWindow;
import javafx.application.Application;

public class GUIRunner implements Runner {

    private void initPreloader() {
        System.setProperty("javafx.preloader", PreloaderWindow.class.getCanonicalName());
    }

    @Override
    public void run() {
        initPreloader();
        Application.launch(ApplicationWindow.class);
    }
}
