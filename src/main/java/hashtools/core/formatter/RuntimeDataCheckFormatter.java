package hashtools.core.formatter;

import hashtools.core.model.Hash;
import hashtools.core.model.RuntimeData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class RuntimeDataCheckFormatter implements Formatter<RuntimeData> {

    @Override
    public String format(RuntimeData runtimeData) {
        List<String> headers   = new ArrayList<>(List.of("Input", "Official", "Generated", "Result"));
        int          padding   = getHigherLength(headers);
        String       delimiter = "-".repeat(padding + 130) + "\n";
        StringJoiner result    = new StringJoiner(delimiter, delimiter, delimiter);

        applyPadding(padding, headers);

        sortHashes(runtimeData).forEach(entry -> {
            String algorithm = entry.getKey();
            Hash   hash      = entry.getValue();

            String content = headers.get(0) + algorithm + '\n' +
                             headers.get(1) + hash.getOfficial() + '\n' +
                             headers.get(2) + hash.getGenerated() + '\n' +
                             headers.get(3) + (hash.matches() ? "Safe" : "Unsafe") + '\n';

            result.add(content);
        });

        return result.toString();
    }

    private void applyPadding(int padding, List<String> headers) {
        headers.replaceAll(s -> String.format("%" + padding + "s: ", s));
    }

    private int getHigherLength(List<String> strings) {
        return strings.stream()
                      .map(String::length)
                      .reduce(Integer::max)
                      .orElse(0);
    }

    private List<Map.Entry<String, Hash>> sortHashes(RuntimeData runtimeData) {
        return runtimeData.getHashes()
                          .entrySet()
                          .stream()
                          .sorted(Comparator.comparingInt(entry -> entry.getValue().getGenerated().length()))
                          .collect(Collectors.toList());
    }
}
