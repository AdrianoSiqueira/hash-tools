package hashtools.core.service;

import hashtools.core.model.RuntimeData;

import java.nio.file.Path;
import java.util.List;

public class CommandLineService {

    public RuntimeData prepareRuntimeData(List<String> arguments) {
        RuntimeData.Builder builder = RuntimeData.newBuilder();

        arguments.forEach(argument -> {
            if (argument.startsWith("--check-file")) {
                builder.checking()
                       .inputFile(Path.of(getValue(argument)));
            } else if (argument.startsWith("--check-text")) {
                builder.checking()
                       .inputText(getValue(argument));
            } else if (argument.startsWith("--generate-file")) {
                builder.generating()
                       .inputFile(Path.of(getValue(argument)));
            } else if (argument.startsWith("--generate-text")) {
                builder.generating()
                       .inputText(getValue(argument));
            } else if (argument.startsWith("--with-file")) {
                builder.officialFile(Path.of(getValue(argument)));
            } else if (argument.startsWith("--with-hash")) {
                builder.officialHash(getValue(argument));
            } else if (argument.startsWith("--algorithms")) {
                String[] algorithms = getValue(argument).split(" ");
                builder.algorithms(algorithms);
            } else if (argument.startsWith("--output-file")) {
                builder.outputFile(Path.of(getValue(argument)));
            }
        });

        return builder.createRuntimeData();
    }

    private String getValue(String argument) {
        String[] values = argument.split("=");

        return values.length == 2
               ? values[1]
               : "";
    }
}
