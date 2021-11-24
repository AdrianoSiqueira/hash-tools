package hashtools.core.module.cli;

import hashtools.core.module.checker.CheckerModule;
import hashtools.core.module.generator.GeneratorModule;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * Allows execution from the command line.
 * </p>
 *
 * TODO Translate all the interaction and Exception messages.
 *
 * @author Adriano Siqueira
 * @version 1.0.1
 * @since 2.0.0
 */
public class ComandLineModule implements Runnable {

    private final String[] arguments;


    /**
     * <p>
     * Creates an instance of {@link ComandLineModule} class.
     * </p>
     *
     * @param arguments Arguments used to run the application.
     *
     * @since 1.0.0
     */
    public ComandLineModule(String[] arguments) {
        this.arguments = Objects.requireNonNull(arguments);
    }


    /**
     * <p>
     * Run the check sequence.
     * </p>
     *
     * @throws IllegalArgumentException If there are any errors in the arguments.
     * @since 1.0.1
     */
    private void runCheckSequence()
    throws IllegalArgumentException {
        if (arguments.length != 3)
            throw new IllegalArgumentException("Wrong number of arguments.");


        System.out.println("HashTools" + System.lineSeparator() +
                           ">> Running: Check Module");

        double percentage = new CheckerModule(arguments[1], arguments[2])
                .call()
                .getReliabilityPercentage();

        System.out.println(">> Execution done." + System.lineSeparator() +
                           "   Reliability: " + percentage + " %");
    }

    /**
     * <p>
     * Run the generation sequence.
     * </p>
     *
     * @throws IllegalArgumentException If there are any errors in the arguments.
     * @since 1.0.1
     */
    private void runGenerationSequence()
    throws IllegalArgumentException {
        if (arguments.length < 4)
            throw new IllegalArgumentException("Wrong number of arguments..");


        System.out.println("HashTools" + System.lineSeparator() +
                           ">> Running: Generation Module");

        String[] algorithms = Arrays.copyOfRange(arguments, 3, arguments.length);

        new GeneratorModule(arguments[1], arguments[2], List.of(algorithms))
                .call();

        System.out.println(">> Execution done.");
    }


    /**
     * <p>
     * Determines the execution mode by analyzing the given arguments.
     * </p>
     *
     * @throws IllegalArgumentException If there are any errors in the arguments.
     * @since 1.0.0
     */
    @Override
    public void run()
    throws IllegalArgumentException {
        if (arguments[0].equalsIgnoreCase("--check")) {
            runCheckSequence();
        } else if (arguments[0].equalsIgnoreCase("--generate")) {
            runGenerationSequence();
        } else {
            throw new IllegalArgumentException("Invalid execution module: '" + arguments[0] + "'");
        }
    }
}
