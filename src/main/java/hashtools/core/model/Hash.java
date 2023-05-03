package hashtools.core.model;

import java.util.Objects;

/**
 * <p>
 * Encapsulates the algorithm and both the official and generated hash
 * checksums together.
 * </p>
 */
public class Hash {

    private String algorithm;
    private String official;
    private String generated;

    public Hash() {
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (getClass() != object.getClass()) return false;

        Hash hash = (Hash) object;

        return Objects.equals(algorithm, hash.algorithm);
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getGenerated() {
        return generated;
    }

    public String getOfficial() {
        return official;
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithm);
    }

    /**
     * <p>
     * Checks whether the hash checksums matches. The checking is case
     * insensitive and returns false if some checksum is null.
     * </p>
     *
     * @return True if both checksums are not null and equals.
     */
    public boolean matches() {
        if (official == null) return false;
        if (generated == null) return false;

        return official.equalsIgnoreCase(generated);
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setGenerated(String generated) {
        this.generated = generated;
    }

    public void setOfficial(String official) {
        this.official = official;
    }
}
