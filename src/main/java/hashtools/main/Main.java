package hashtools.main;

import hashtools.core.runner.CLIRunner;
import hashtools.core.runner.GUIRunner;
import hashtools.core.runner.Runner;
import hashtools.core.service.ParallelismService;

public class Main {

    public static void main(String[] args) {
        scheduleParallelismServiceShutdown();

        Runner runner = (args.length == 0)
                        ? new GUIRunner()
                        : new CLIRunner(args);

        runner.run();
    }

    private static void scheduleParallelismServiceShutdown() {
        Runtime.getRuntime()
               .addShutdownHook(new Thread(ParallelismService::shutdown));
    }
}
