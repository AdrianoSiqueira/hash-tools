package hashtools.core.module.generator;

import hashtools.core.model.Sample;
import hashtools.core.service.HashService;

import java.nio.file.Path;
import java.util.function.Consumer;

public class HashGenerator implements Consumer<Sample> {

    @Override
    public void accept(Sample sample) {
        HashService service = new HashService();

        String hash = sample.isUsingFileAsObject()
                      ? service.generate(sample.getAlgorithm(), (Path) sample.getObject())
                      : service.generate(sample.getAlgorithm(), (String) sample.getObject());

        sample.setCalculatedHash(hash);
    }
}
