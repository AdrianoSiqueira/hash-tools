package hashtools.threadpool;

public class UserThreadFactory extends AbstractThreadFactory {

    private static final boolean IS_DAEMON = false;

    public UserThreadFactory(String name) {
        super(name, IS_DAEMON);
    }
}
