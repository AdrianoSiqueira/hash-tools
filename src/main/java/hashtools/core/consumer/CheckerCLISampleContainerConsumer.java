package hashtools.core.consumer;

import hashtools.core.language.LanguageManager;
import hashtools.core.model.HashAlgorithm;
import hashtools.core.model.SampleContainer;

import java.util.StringJoiner;

public class CheckerCLISampleContainerConsumer implements SampleContainerConsumer {

    @Override
    public void accept(SampleContainer sampleContainer) {
        System.out.println(formatResult(sampleContainer));
        System.out.println(">> " + LanguageManager.get("Reliability") + ": " + sampleContainer.getReliabilityPercentage() + "%");
    }

    @Override
    public String formatResult(SampleContainer sampleContainer) {
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
                                            algorithm + s.getAlgorithm() + ls +
                                            official + s.getOfficialHash() + ls +
                                            calculated + s.getCalculatedHash() + ls +
                                            result + s.hashesMatches() + ls;

                           joiner.add(content);
                       });

        return joiner.toString();
    }
}
