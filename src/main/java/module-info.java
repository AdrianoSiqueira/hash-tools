module hash.tools {
    requires static lombok;
    requires ch.qos.logback.classic;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;

    exports hashtools.application;
    exports hashtools.checker;
    exports hashtools.comparator;
    exports hashtools.generator;
    exports hashtools.service;
    exports hashtools.shared.condition;
    exports hashtools.shared.messagedigest;
    exports hashtools.shared.notification;
    exports hashtools.shared.operation;
    exports hashtools.shared.threadpool;
    exports hashtools.shared;

    opens hashtools.application;
    opens hashtools.checker;
    opens hashtools.comparator;
    opens hashtools.generator;
    opens hashtools.service;
    opens hashtools.shared.condition;
    opens hashtools.shared.messagedigest;
    opens hashtools.shared.notification;
    opens hashtools.shared.operation;
    opens hashtools.shared.threadpool;
    opens hashtools.shared;
}
