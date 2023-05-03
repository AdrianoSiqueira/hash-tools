package hashtools.core.operation.data;

import hashtools.core.model.Data;
import hashtools.core.model.Hash;

import java.util.List;

/**
 * <p>
 * Creates the {@link Hash} list from the official data within
 * the {@link Data} object.
 * </p>
 */
public class HashesLoaderDataOperation implements DataOperation {

    @Override
    public void perform(Data data) {
        List<Hash> hashes = data.getFactory().create();
        data.setHashes(hashes);
    }
}
