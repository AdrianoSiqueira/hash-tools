module hash.tools {
    requires static lombok;

    requires javafx.fxml;
    requires javafx.controls;
    requires org.slf4j;

    exports hashtools.gui.screen.splash;
    exports hashtools.gui.window.about;
    exports hashtools.gui.window.application;
    exports hashtools.gui.window.preloader;

    opens hashtools.gui.screen.splash;
    opens hashtools.gui.window.about;
    opens hashtools.gui.window.application;
}
