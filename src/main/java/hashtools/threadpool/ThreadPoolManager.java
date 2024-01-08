package hashtools.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

public class ThreadPoolManager {

    private static ExecutorService create(String name, boolean isDaemon) {
        return Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            new ThreadPoolFactory(name, isDaemon)
        );
    }

    private static String getCallerName() {
        /*
         * This predicate removes this class from the caller
         * stream, allowing to get the imediate caller class
         * even if this method is called internally multiple
         * times.
         */
        Predicate<Class<?>> removeThisClass = clazz -> !clazz
            .getName()
            .equals(ThreadPoolManager.class.getName());

        return StackWalker
            .getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
            .walk(stream -> stream
                .map(StackWalker.StackFrame::getDeclaringClass)
                .filter(removeThisClass)
                .limit(1)
                .findFirst()
                .map(Class::getSimpleName)
                .orElse(null));
    }

    public static ExecutorService newDaemon(String name) {
        return create(name, true);
    }

    public static ExecutorService newDefault(String name) {
        return create(name, false);
    }
}
