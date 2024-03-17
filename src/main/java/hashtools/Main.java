package hashtools;

import hashtools.controller.ApplicationController;
import hashtools.controller.PreloaderController;
import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
//        Locale.setDefault(new Locale("pt", "br"));

        System.setProperty("javafx.preloader", PreloaderController.class.getCanonicalName());
        Application.launch(ApplicationController.class);
    }
}
