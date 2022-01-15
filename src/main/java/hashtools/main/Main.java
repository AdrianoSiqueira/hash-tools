package hashtools.main;

import hashtools.core.module.cli.ComandLineModule;
import hashtools.gui.window.MainWindow;
import javafx.application.Platform;

/**
 * <p>Application main class.</p>
 *
 * @author Adriano Siqueira
 * @version 2.0.0
 * @since 1.0.0
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) Platform.startup(MainWindow::new);
        else new ComandLineModule(args).run();
    }
}
