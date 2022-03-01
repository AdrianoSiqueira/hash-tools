package hashtools.core.module.cli;

import hashtools.core.language.LanguageManager;
import hashtools.core.module.checker.CheckerModule;
import hashtools.core.module.generator.GeneratorModule;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ComandLineModule implements Runnable {

    private final String[] arguments;


    public ComandLineModule(String[] arguments) {
        this.arguments = Objects.requireNonNull(arguments);
    }


    private void runCheckSequence()
    throws IllegalArgumentException {
        if (arguments.length != 3)
            throw new IllegalArgumentException(LanguageManager.get("Wrong.number.of.arguments."));


        System.out.println("HashTools" + System.lineSeparator() +
                           ">> " + LanguageManager.get("Running") + ": " + LanguageManager.get("Check.Module."));

        double percentage = new CheckerModule(arguments[1], arguments[2])
                .call()
                .getReliabilityPercentage();

        System.out.println(">> " + LanguageManager.get("Execution.done.") + System.lineSeparator() +
                           "   " + LanguageManager.get("Reliability") + ": " + percentage + " %");
    }

    private void runGenerationSequence()
    throws IllegalArgumentException {
        if (arguments.length < 4)
            throw new IllegalArgumentException(LanguageManager.get("Wrong.number.of.arguments."));


        System.out.println("HashTools" + System.lineSeparator() +
                           ">> " + LanguageManager.get("Running") + ": " + LanguageManager.get("Generation.Module."));

        String[] algorithms = Arrays.copyOfRange(arguments, 3, arguments.length);

        new GeneratorModule(arguments[1], arguments[2], List.of(algorithms))
                .call();

        System.out.println(">> " + LanguageManager.get("Execution.done."));
    }


    @Override
    public void run()
    throws IllegalArgumentException {
        if (arguments[0].equalsIgnoreCase("--check")) {
            runCheckSequence();
        } else if (arguments[0].equalsIgnoreCase("--generate")) {
            runGenerationSequence();
        } else {
            throw new IllegalArgumentException(LanguageManager.get("Invalid.execution.module") + ": '" + arguments[0] + "'");
        }
    }
}
