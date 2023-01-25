package hashtools.core.operation.data;

import hashtools.core.model.Data;
import hashtools.core.model.Hash;

import java.util.List;

public class HashesLoaderDataOperation implements DataOperation {

    @Override
    public void perform(Data data) {
        List<Hash> hashes = data.getFactory().create();
        data.setHashes(hashes);
    }
}
