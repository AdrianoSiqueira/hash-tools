module hash.tools {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.slf4j;

    exports hashtools.condition;
    exports hashtools.controller;
    exports hashtools.domain;
    exports hashtools.operation;
    exports hashtools.window;

    opens hashtools.condition;
    opens hashtools.controller;
    opens hashtools.domain;
    opens hashtools.operation;
    opens hashtools.window;
}
