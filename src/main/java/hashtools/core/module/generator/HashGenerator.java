package hashtools.core.module.generator;

import hashtools.core.model.Sample;
import hashtools.core.service.FileService;
import hashtools.core.service.HashService;

import java.nio.file.Path;
import java.util.function.Consumer;

public class HashGenerator implements Consumer<Sample> {

    @Override
    public void accept(Sample sample) {
        HashService service = new HashService();

        String hash = new FileService().stringIsFilePath(sample.getInputData())
                      ? service.generate(sample.getAlgorithm(), Path.of(sample.getInputData()))
                      : service.generate(sample.getAlgorithm(), sample.getInputData());

        sample.setCalculatedHash(hash);
    }
}
