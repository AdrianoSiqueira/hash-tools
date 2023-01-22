package hashtools.core.operation.data;

import hashtools.core.model.Data;
import hashtools.core.service.HashService;

/**
 * TODO Implement parallelism service in this class.
 */
public class HashGeneratorDataOperation implements DataOperation {

    @Override
    public void perform(Data data) {
        HashService service = new HashService();

        data.getHashes()
            .forEach(hash -> hash.setGenerated(
                    data.isUsingInputFile()
                    ? service.generate(hash.getAlgorithm(), data.getInputFile())
                    : service.generate(hash.getAlgorithm(), data.getInputText())
            ));
    }
}
