package hashtools.core.module.runner;

import hashtools.core.consumer.CheckerCLISampleContainerConsumer;
import hashtools.core.consumer.GeneratorCLISampleContainerConsumer;
import hashtools.core.language.LanguageManager;
import hashtools.core.model.Environment;
import hashtools.core.model.HashAlgorithm;
import hashtools.core.model.RunMode;
import hashtools.core.module.Runner;
import hashtools.core.service.HashAlgorithmService;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class CommandLineInterface implements Runnable {

    private final String[] arguments;


    @Override
    public void run() {
        RunMode runMode = switch (arguments[0].toLowerCase()) {
            case "--check" -> RunMode.CHECKER;
            case "--generate" -> RunMode.GENERATOR;
            default -> throw new IllegalArgumentException(LanguageManager.get("Invalid.execution.module") + ": '" + arguments[0] + "'");
        };

        if (runMode == RunMode.CHECKER && arguments.length != 3 ||
            runMode == RunMode.GENERATOR && arguments.length < 4) {
            throw new IllegalArgumentException("Wrong.number.of.arguments.");
        }

        Environment environment = new Environment();
        environment.setRunMode(runMode);
        environment.setInputData(arguments[1]);

        if (runMode == RunMode.CHECKER) {
            environment.setOfficialData(arguments[2]);
            environment.setConsumer(new CheckerCLISampleContainerConsumer());
        } else {
            environment.setOutputData(arguments[2]);
            environment.setConsumer(new GeneratorCLISampleContainerConsumer());

            List<HashAlgorithm> algorithms = new HashAlgorithmService().convertToAlgorithmList(Arrays.copyOfRange(arguments, 3, arguments.length));
            environment.setAlgorithms(algorithms);
        }

        new Runner(environment).run();
    }
}
