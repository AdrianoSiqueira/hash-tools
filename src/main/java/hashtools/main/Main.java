package hashtools.main;

import hashtools.core.module.cli.ComandLineModule;
import hashtools.gui.window.application.ApplicationWindow;
import hashtools.gui.window.preloader.PreloaderWindow;
import javafx.application.Application;

/**
 * <p>
 * Application main class.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 2.2.0
 * @since 1.0.0
 */
public class Main {

    public static void main(String[] args) {
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
}
