module hash.tools {
    requires lombok;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.slf4j;
    requires java.sql;

    exports hashtools.controller.application;
    exports hashtools.controller;
    exports hashtools.dialog;

    opens hashtools.controller.application;
    opens hashtools.controller;
    opens hashtools.dialog;
}
