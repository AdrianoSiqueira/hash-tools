package hashtools.core.factory.data;

import hashtools.core.model.Data;

import java.nio.file.Path;
import java.util.List;

public class ArgumentDataFactory implements DataFactory {

    private final List<String> arguments;

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

    private String getValue(String argument) {
        String[] values = argument.split("=");

        return values.length == 2
               ? values[1]
               : "";
    }
}
