package hashtools.main;

import hashtools.core.module.cli.ComandLineModule;
import hashtools.gui.screen.application.ApplicationWindow;
import javafx.application.Application;

/**
 * <p>Application main class.</p>
 *
 * @author Adriano Siqueira
 * @version 2.1.0
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
        // TODO Add preloader support
//        System.setProperty("javafx.preloader",null);

        Application.launch(ApplicationWindow.class);
//        Platform.startup(MainWindow::new); // TODO Remove this statement
    }
}
