package hashtools.core.formatter.data;

import hashtools.core.formatter.Formatter;
import hashtools.core.model.Data;
import hashtools.core.model.Hash;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * Specialization of interface {@link Formatter} destined to format objects
 * of type {@link Data}. This interface introduces a method to sort a list
 * of {@link Hash}.
 * </p>
 */
public abstract class DataFormatter implements Formatter<Data> {

    /**
     * <p>
     * Sorts the hashes according to the length of the generated hash
     * checksum.
     * </p>
     *
     * @param hashes Hashes that will be sorted.
     *
     * @return A sorted {@link Hash} list.
     */
    protected List<Hash> sortHashes(List<Hash> hashes) {
        return hashes.stream()
                     .sorted(Comparator.comparingInt(hash -> hash.getGenerated().length()))
                     .collect(Collectors.toList());
    }
}
