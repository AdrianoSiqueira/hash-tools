module hash.tools {
    requires static lombok;
    requires ch.qos.logback.classic;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;

    exports hashtools.applicationmodule.application;
    exports hashtools.applicationmodule.checker.condition;
    exports hashtools.applicationmodule.checker.officialchecksum;
    exports hashtools.applicationmodule.checker;
    exports hashtools.applicationmodule.comparator;
    exports hashtools.applicationmodule.generator;
    exports hashtools.applicationmodule.preloader;
    exports hashtools.coremodule.condition;
    exports hashtools.coremodule.identification;
    exports hashtools.coremodule.messagedigest;
    exports hashtools.coremodule.notification;
    exports hashtools.coremodule.operation;
    exports hashtools.coremodule.threadpool;
    exports hashtools.coremodule;

    opens hashtools.applicationmodule.application;
    opens hashtools.applicationmodule.checker.condition;
    opens hashtools.applicationmodule.checker.officialchecksum;
    opens hashtools.applicationmodule.checker;
    opens hashtools.applicationmodule.comparator;
    opens hashtools.applicationmodule.generator;
    opens hashtools.applicationmodule.preloader;
    opens hashtools.coremodule.condition;
    opens hashtools.coremodule.identification;
    opens hashtools.coremodule.messagedigest;
    opens hashtools.coremodule.notification;
    opens hashtools.coremodule.operation;
    opens hashtools.coremodule.threadpool;
    opens hashtools.coremodule;
}
