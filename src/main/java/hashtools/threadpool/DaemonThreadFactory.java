package hashtools.threadpool;

public class DaemonThreadFactory extends AbstractThreadFactory {

    private static final boolean IS_DAEMON = true;

    public DaemonThreadFactory(String name) {
        super(name, IS_DAEMON);
    }
}
