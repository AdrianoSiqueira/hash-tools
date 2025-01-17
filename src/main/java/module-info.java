module hash.tools {
    requires static lombok;
    requires ch.qos.logback.classic;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;

    exports hashtools.application;
    exports hashtools.checker.condition;
    exports hashtools.checker.officialchecksum;
    exports hashtools.checker;
    exports hashtools.comparator;
    exports hashtools.generator;
    exports hashtools.preloader;
    exports hashtools.coremodule.condition;
    exports hashtools.coremodule.identification;
    exports hashtools.coremodule.messagedigest;
    exports hashtools.coremodule.notification;
    exports hashtools.coremodule.operation;
    exports hashtools.coremodule.threadpool;
    exports hashtools.coremodule;

    opens hashtools.application;
    opens hashtools.checker.condition;
    opens hashtools.checker.officialchecksum;
    opens hashtools.checker;
    opens hashtools.comparator;
    opens hashtools.generator;
    opens hashtools.preloader;
    opens hashtools.coremodule.condition;
    opens hashtools.coremodule.identification;
    opens hashtools.coremodule.messagedigest;
    opens hashtools.coremodule.notification;
    opens hashtools.coremodule.operation;
    opens hashtools.coremodule.threadpool;
    opens hashtools.coremodule;
}
