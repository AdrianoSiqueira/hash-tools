package hashtools.core.formatter;

import hashtools.core.model.Hash;
import hashtools.core.model.RuntimeData;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class RuntimeDataGenerateFormatter implements RuntimeDataFormatter {

    @Override
    public String format(RuntimeData runtimeData) {
        String name = runtimeData.isUsingInputFile()
                      ? runtimeData.getInputFile().getFileName().toString()
                      : runtimeData.getInputText();

        StringJoiner result = new StringJoiner("\n");

        sortHashes(runtimeData)
                .stream()
                .map(Map.Entry::getValue)
                .forEach(hash -> {
                    String content = hash.getGenerated() + "  " + name + '\n';

                    result.add(content);
                });

        return result.toString();
    }

    private List<Map.Entry<String, Hash>> sortHashes(RuntimeData runtimeData) {
        return runtimeData.getHashes()
                          .entrySet()
                          .stream()
                          .sorted(Comparator.comparingInt(entry -> entry.getValue().getGenerated().length()))
                          .collect(Collectors.toList());
    }
}
