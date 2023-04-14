package hashtools.main;

import hashtools.core.runner.CLIRunner;
import hashtools.core.runner.GUIRunner;
import hashtools.core.runner.Runner;

public class Main {

    public static void main(String[] args) {
        Runner runner = (args.length == 0)
                        ? new GUIRunner()
                        : new CLIRunner(args);

        runner.run();
    }
}
