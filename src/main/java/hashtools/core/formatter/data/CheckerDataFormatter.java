package hashtools.core.formatter.data;

import hashtools.core.model.Data;
import hashtools.core.service.LanguageService;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * <p>
 * Formats the {@link Data} for the checker module. All the data will
 * be included. This class implements the {@link LanguageService}
 * providing localized result.
 * </p>
 */
public class CheckerDataFormatter extends DataFormatter {

    private LanguageService languageService;

    /**
     * <p>
     * Creates an instance of {@link CheckerDataFormatter} initializing
     * the {@link LanguageService}.
     * </p>
     */
    public CheckerDataFormatter() {
        languageService = new LanguageService();
    }

    /**
     * <p>
     * Applies the padding to all headers.
     * </p>
     *
     * @param padding Padding to apply.
     * @param headers Where to apply the padding.
     */
    private void applyPadding(int padding, List<String> headers) {
        headers.replaceAll(s -> String.format("%" + padding + "s: ", s));
    }

    @Override
    public String format(Data data) {
        if (data == null)
            return null;

        List<String> headers = new ArrayList<>(List.of(
                languageService.get("Algorithm"),
                languageService.get("Official"),
                languageService.get("Generated"),
                languageService.get("Result")
        ));

        int          padding   = getHigherLength(headers);
        String       delimiter = "-".repeat(padding + 130) + "\n";
        StringJoiner result    = new StringJoiner(delimiter, delimiter, delimiter);

        applyPadding(padding, headers);

        sortHashes(data.getHashes()).forEach(hash -> {
            String content = headers.get(0) + hash.getAlgorithm() + '\n' +
                             headers.get(1) + hash.getOfficial() + '\n' +
                             headers.get(2) + hash.getGenerated() + '\n' +
                             headers.get(3) + (hash.matches() ? languageService.get("Safe") : languageService.get("Unsafe")) + '\n';

            result.add(content);
        });

        String percentageContent = String.format(
                "%s: %.2f %%",
                languageService.get("Safety.Percentage"),
                data.getSafetyPercentage()
        );

        return result.toString().concat(percentageContent);
    }

    /**
     * <p>
     * Gets the higher length of the strings. It is used to determine
     * the padding that will be applied to the headers.
     * </p>
     *
     * @param strings Used to determine the higher length.
     *
     * @return The higher length.
     */
    private int getHigherLength(List<String> strings) {
        return strings.stream()
                      .map(String::length)
                      .reduce(Integer::max)
                      .orElse(0);
    }
}
