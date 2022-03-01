package hashtools.core.service;

import hashtools.core.model.HashAlgorithm;
import hashtools.core.model.Sample;
import hashtools.core.module.generator.FileHashGenerator;
import hashtools.core.module.generator.StringHashGenerator;

import java.nio.file.Path;

public class HashService {

    public void generate(Sample sample) {
        if (sample == null) return;

        String hash = new FileService().stringIsFilePath(sample.getInputData())
                      ? generate(sample.getAlgorithm(), Path.of(sample.getInputData()))
                      : generate(sample.getAlgorithm(), sample.getInputData());

        sample.setCalculatedHash(hash);
    }

    public String generate(HashAlgorithm algorithm, String string) {
        return new StringHashGenerator().generate(algorithm, string);
    }

    public String generate(HashAlgorithm algorithm, Path path) {
        return new FileHashGenerator().generate(algorithm, path);
    }
}
