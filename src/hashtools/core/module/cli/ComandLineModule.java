package hashtools.core.module.cli;

import hashtools.core.module.checker.CheckerModule;
import hashtools.core.module.generator.GeneratorModule;

import java.util.Arrays;
import java.util.Objects;

/**
 * <p>Allows execution from the command line.</p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class ComandLineModule implements Runnable {

    private final String[] arguments;

    public ComandLineModule(String[] arguments) {
        this.arguments = Objects.requireNonNull(arguments);
    }


    @Override
    public void run() {
        if (arguments[0].equalsIgnoreCase("--check")) {
            if (arguments.length != 3) throw new IllegalArgumentException();

            // TODO Remove this line
            System.out.println("Checking");

            double score = new CheckerModule(arguments[1], arguments[2])
                    .call()
                    .getReliabilityPercentage();

            System.out.println("Total score: " + score);
        } else if (arguments[0].equalsIgnoreCase("--generate")) {
            if (arguments.length < 4) throw new IllegalArgumentException();

            // TODO Remove this line
            System.out.println("Generating");

            String[] algorithms = Arrays.copyOfRange(arguments, 3, arguments.length);

            new GeneratorModule(arguments[1], arguments[2], algorithms)
                    .call();
        }
    }
}
