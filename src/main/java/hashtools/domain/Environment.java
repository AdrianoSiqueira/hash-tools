package hashtools.domain;

public final class Environment {

    public static final class Hardware {
        public static final int CPU = Runtime.getRuntime().availableProcessors();
    }

    public static final class Software {
        public static final String VERSION = "3.0.0";
    }
}
