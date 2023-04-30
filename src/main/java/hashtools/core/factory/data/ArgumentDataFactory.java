package hashtools.core.factory.data;

import hashtools.core.model.Data;

import java.nio.file.Path;
import java.util.List;

/**
 * <p>
 * Creates objects of type {@link Data} using the arguments from the
 * command line.
 * </p>
 */
public class ArgumentDataFactory implements DataFactory {

    private final List<String> arguments;

    /**
     * <p>
     * Creates an instance of the {@link ArgumentDataFactory} that will
     * process the arguments to create the objects of type {@link Data}.
     * </p>
     *
     * @param arguments Will be used to create the {@link Data} object.
     */
    public ArgumentDataFactory(List<String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public Data create() {
        Data data = new Data();

        if (arguments == null)
            return data;

        arguments.forEach(argument -> {
            if (argument.startsWith("--check-file")) {
                data.setChecking();
                data.setInputFile(Path.of(getValue(argument)));
            } else if (argument.startsWith("--check-text")) {
                data.setChecking();
                data.setInputText(getValue(argument));
            } else if (argument.startsWith("--generate-file")) {
                data.setGenerating();
                data.setInputFile(Path.of(getValue(argument)));
            } else if (argument.startsWith("--generate-text")) {
                data.setGenerating();
                data.setInputText(getValue(argument));
            } else if (argument.startsWith("--with-file")) {
                data.setOfficialFile(Path.of(getValue(argument)));
            } else if (argument.startsWith("--with-hash")) {
                data.setOfficialHash(getValue(argument));
            } else if (argument.startsWith("--algorithms")) {
                data.setAlgorithms(getValue(argument).split(" "));
            } else if (argument.startsWith("--output-file")) {
                data.setOutputFile(Path.of(getValue(argument)));
            }
        });

        return data;
    }

    /**
     * <p>
     * Gets the value after the '=' sign from the given string.
     * If the value is not present an empty string will be returned.
     * </p>
     *
     * @param argument Where to get the value.
     *
     * @return The value after the '=' sign.
     */
    private String getValue(String argument) {
        String[] values = argument.split("=");

        return values.length == 2
               ? values[1]
               : "";
    }
}
