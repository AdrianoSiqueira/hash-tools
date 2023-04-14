package hashtools.core.service;

/**
 * <p style="text-align:justify">
 * This class performs operations involving hash checksum algorithms.
 * </p>
 */
public class AlgorithmService {

    /**
     * <p style="text-align:justify">
     * Gets the algorithm from its length.
     * </p>
     *
     * <p style="text-align:justify">
     * This method returns null if the algorithm cannot be determined.
     * </p>
     *
     * @param length Used to determine the algorithm.
     *
     * @return The algorithm in {@link String} format.
     */
    public String getAlgorithm(int length) {
        switch (length) {
            case 32:
                return "MD5";
            case 40:
                return "SHA-1";
            case 56:
                return "SHA-224";
            case 64:
                return "SHA-256";
            case 96:
                return "SHA-384";
            case 128:
                return "SHA-512";
            default:
                return null;
        }
    }

    /**
     * <p style="text-align:justify">
     * Gets the algorithm from its name. This method removes all non
     * alphanumerical characters and it is not case sensitive.
     * </p>
     *
     * <p style="text-align:justify">
     * This method returns null if the algorithm cannot be determined.
     * </p>
     *
     * @param name Used to determine the algorithm.
     *
     * @return The algorithm in {@link String} format.
     */
    public String getAlgorithm(String name) {
        if (name == null)
            return null;

        switch (name.replaceAll("[^a-zA-Z0-9]", "").toLowerCase()) {
            case "md5":
                return "MD5";
            case "sha1":
                return "SHA-1";
            case "sha224":
                return "SHA-224";
            case "sha256":
                return "SHA-256";
            case "sha384":
                return "SHA-384";
            case "sha512":
                return "SHA-512";
            default:
                return null;
        }
    }
}
