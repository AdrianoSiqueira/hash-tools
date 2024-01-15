package hashtools.threadpool;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Predicate;

@RequiredArgsConstructor
public enum ThreadPoolFactory {

    DAEMON(true),
    DEFAULT(false);

    private final boolean isDaemon;

    public ExecutorService create() {
        return Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            new ThreadFactoryImpl(getCallerClassName(), isDaemon)
        );
    }

    /**
     * <p style="text-align:justify">
     * Retrieves the name of the class that called it. It access the call
     * stack and gets the last class before this method call. The JVM
     * stores internal calls in that stack too, making the last class be
     * itself. Such behavior is reproduced in the following scenario:
     * </p>
     *
     * <pre>{@code
     * public class WithoutFilter {
     *
     *     private static class Class1 {
     *         public static void run() {
     *             Class2.run();
     *         }
     *     }
     *
     *     private static class Class2 {
     *         public static void run() {
     *             Class2.getCaller();
     *         }
     *
     *         public static String getCaller() {
     *             // ...
     *         }
     *     }
     * }
     * }</pre>
     *
     * <p style="text-align:justify">
     * As the method is called within the same class, the last caller
     * class is itself. To prevent this behavior, the method removes
     * itself from the call stack, making the following scenario return
     * the right result.
     * </p>
     *
     * <pre>{@code
     * public class WithFilter {
     *
     *     private static class Class1 {
     *         public static void run() {
     *             Class2.run();
     *         }
     *     }
     *
     *     private static class Class2 {
     *         public static void run() {
     *             Class3.run();
     *         }
     *     }
     *
     *     private static class Class3 {
     *         public static void run() {
     *             Class3.getCaller();
     *         }
     *
     *         public static String getCaller() {
     *             // ...
     *         }
     *     }
     * }
     * }</pre>
     *
     * <p style="text-align:justify">
     * In this scenario, the method will report the <code>Class2</code>
     * as the caller class.
     * </p>
     */
    private String getCallerClassName() {
        Predicate<Class<?>> removeItself = clazz -> !clazz
            .getName()
            .equals(ThreadPoolFactory.class.getName());

        return StackWalker
            .getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
            .walk(stream -> stream
                .map(StackWalker.StackFrame::getDeclaringClass)
                .filter(removeItself)
                .limit(1)
                .findFirst()
                .map(Class::getSimpleName)
                .orElse(null));
    }


    private static class ThreadFactoryImpl implements ThreadFactory {

        private ThreadGroup group;
        private boolean     isDaemon;

        public ThreadFactoryImpl(String name, boolean isDaemon) {
            this.group    = new ThreadGroup(name);
            this.isDaemon = isDaemon;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            String threadName = String.format(
                "%s_%d",
                group.getName(),
                group.activeCount()
            );

            Thread thread = new Thread(group, runnable, threadName);
            thread.setDaemon(isDaemon);
            return thread;
        }
    }
}
