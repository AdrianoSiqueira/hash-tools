module HashTools {
    requires javafx.fxml;
    requires javafx.controls;
    requires ASLib;

    exports hashtools.gui.screen.about;
    exports hashtools.gui.screen.checker;
    exports hashtools.gui.screen.generator;
    exports hashtools.gui.screen.manual;
    exports hashtools.gui.screen.splash;
    exports hashtools.gui.window.application;
    exports hashtools.gui.window.preloader;

    opens hashtools.gui.screen.about;
    opens hashtools.gui.screen.checker;
    opens hashtools.gui.screen.generator;
    opens hashtools.gui.screen.manual;
    opens hashtools.gui.screen.splash;
    opens hashtools.gui.window.application;
}
