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

    /**
     * <p style="text-align:justify">
     * Retrieves the name of the class that called it. It access he call
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
    private static String getCallerName() {
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

    public static ExecutorService newDaemon() {
        return create(getCallerName(), true);
    }

    public static ExecutorService newDefault(String name) {
        return create(name, false);
    }

    public static ExecutorService newDefault() {
        return create(getCallerName(), false);
    }
}
