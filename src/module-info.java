module HashTools {
    requires ASLib;
    requires java.logging;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens controller;

    exports main;
    exports controller;
}