package hashtools.main;

import hashtools.core.runner.CLIRunner;
import hashtools.core.runner.GUIRunner;
import hashtools.core.runner.Runner;

/**
 * <p>
 * Application main class. It determines the running mode based on
 * the content of the arguments from the command line.
 * </p>
 */
public class Main {

    public static void main(String[] args) {
        Runner runner = (args.length == 0)
                        ? new GUIRunner()
                        : new CLIRunner(args);

        runner.run();
    }
}
