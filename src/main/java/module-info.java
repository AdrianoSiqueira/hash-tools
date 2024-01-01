module hash.tools {
    requires lombok;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.slf4j;

    exports hashtools.controller;
    opens hashtools.controller;
}
