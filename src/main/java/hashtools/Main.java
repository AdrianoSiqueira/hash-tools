package hashtools;

import hashtools.controller.application.ApplicationController;
import hashtools.controller.PreloaderController;
import hashtools.domain.Environment;
import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
//        Locale.setDefault(new Locale("pt", "br"));

        shutdownThreadPoolOnClose();

        System.setProperty("javafx.preloader", PreloaderController.class.getCanonicalName());
        Application.launch(ApplicationController.class);
    }

    private static void shutdownThreadPoolOnClose() {
        Runtime
            .getRuntime()
            .addShutdownHook(new Thread(Environment.Software.THREAD_POOL::shutdownNow));
    }
}
