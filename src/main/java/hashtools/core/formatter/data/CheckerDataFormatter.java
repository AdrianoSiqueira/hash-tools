package hashtools.core.formatter.data;

import hashtools.core.model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class CheckerDataFormatter extends DataFormatter {

    private void applyPadding(int padding, List<String> headers) {
        headers.replaceAll(s -> String.format("%" + padding + "s: ", s));
    }

    @Override
    public String format(Data data) {
        if (data == null)
            return null;

        List<String> headers   = new ArrayList<>(List.of("Algorithm", "Official", "Generated", "Result"));
        int          padding   = getHigherLength(headers);
        String       delimiter = "-".repeat(padding + 130) + "\n";
        StringJoiner result    = new StringJoiner(delimiter, delimiter, delimiter);

        applyPadding(padding, headers);

        sortHashes(data.getHashes()).forEach(hash -> {
            String content = headers.get(0) + hash.getAlgorithm() + '\n' +
                             headers.get(1) + hash.getOfficial() + '\n' +
                             headers.get(2) + hash.getGenerated() + '\n' +
                             headers.get(3) + (hash.matches() ? "Safe" : "Unsafe") + '\n';

            result.add(content);
        });

        String percentageContent = String.format(
                "Safety Percentage: %.2f %%",
                data.getSafetyPercentage()
        );

        return result.toString().concat(percentageContent);
    }

    private int getHigherLength(List<String> strings) {
        return strings.stream()
                      .map(String::length)
                      .reduce(Integer::max)
                      .orElse(0);
    }
}
