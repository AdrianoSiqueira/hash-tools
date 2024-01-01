package hashtools;

import hashtools.controller.Preloader;
import hashtools.language.Language;
import javafx.application.Application;

import java.util.Locale;

public class Main {

    public static void main(String[] args) {
//        Locale.setDefault(new Locale("pt", "br"));
        Language.init();

        System.setProperty("javafx.preloader", Preloader.class.getCanonicalName());
        Application.launch(hashtools.controller.Application.class);
    }
}
