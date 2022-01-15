module HashTools {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires ASLib;
    requires java.logging;

    opens hashtools.gui.window.about;
    opens hashtools.gui.window.application;
    opens hashtools.gui.window.manual;

    exports hashtools.gui.window.about;
    exports hashtools.gui.window.application;
    exports hashtools.gui.window.manual;
    exports hashtools.main;
}
