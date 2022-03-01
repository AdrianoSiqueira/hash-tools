package hashtools.main;

import hashtools.core.module.cli.ComandLineModule;
import hashtools.core.service.ParallelismService;
import hashtools.gui.window.application.ApplicationWindow;
import hashtools.gui.window.preloader.PreloaderWindow;
import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
        scheduleParallelismServiceShutdown();

        if (args.length == 0) runInGuiMode();
        else runInCliMode(args);
    }


    private static void runInCliMode(String[] args) {
        new ComandLineModule(args).run();
    }

    private static void runInGuiMode() {
        System.setProperty("javafx.preloader", PreloaderWindow.class.getCanonicalName());

        Application.launch(ApplicationWindow.class);
    }

    private static void scheduleParallelismServiceShutdown() {
        Runtime.getRuntime()
               .addShutdownHook(new Thread(ParallelismService::shutdown));
    }
}
