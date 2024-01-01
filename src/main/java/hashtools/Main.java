package hashtools;

import hashtools.controller.Preloader;
import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", Preloader.class.getCanonicalName());
        Application.launch(hashtools.controller.Application.class);
    }
}
