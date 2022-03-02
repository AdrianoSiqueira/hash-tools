package hashtools.core.consumer;

import hashtools.core.language.LanguageManager;
import hashtools.core.model.HashAlgorithm;
import hashtools.core.model.SampleContainer;

import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface SampleContainerConsumer extends Consumer<SampleContainer> {

    default String formatResult(SampleContainer sampleContainer) {
        String s1 = LanguageManager.get("Algorithm");
        String s2 = LanguageManager.get("Calculated");
        String s3 = LanguageManager.get("Official");
        String s4 = LanguageManager.get("Result");

        int idealSize = getLargerLength(s1, s2, s3, s4);

        String algorithm  = String.format("%" + idealSize + "s: ", s1);
        String calculated = String.format("%" + idealSize + "s: ", s2);
        String official   = String.format("%" + idealSize + "s: ", s3);
        String result     = String.format("%" + idealSize + "s: ", s4);

        StringJoiner joiner = new StringJoiner("-".repeat(idealSize + HashAlgorithm.SHA512.getLength() + 2),
                                               "-".repeat(idealSize + HashAlgorithm.SHA512.getLength() + 2),
                                               "-".repeat(idealSize + HashAlgorithm.SHA512.getLength() + 2));

        sampleContainer.getSamples()
                       .forEach(s -> {
                           String ls = System.lineSeparator();

                           String content = ls +
                                            algorithm + s.getAlgorithm().getName() + ls +
                                            official + s.getOfficialHash() + ls +
                                            calculated + s.getCalculatedHash() + ls +
                                            result + s.getResult().getText() + ls;

                           joiner.add(content);
                       });

        return joiner.toString();
    }

    default int getLargerLength(String... strings) {
        return Stream.of(strings)
                     .map(String::length)
                     .reduce(Math::max)
                     .orElse(0);
    }
}
