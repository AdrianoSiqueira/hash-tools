package hashtools.core.formatter.data;

import hashtools.core.formatter.Formatter;
import hashtools.core.model.Data;
import hashtools.core.model.Hash;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DataFormatter implements Formatter<Data> {

    protected List<Hash> sortHashes(List<Hash> hashes) {
        return hashes.stream()
                     .sorted(Comparator.comparingInt(hash -> hash.getGenerated().length()))
                     .collect(Collectors.toList());
    }
}
