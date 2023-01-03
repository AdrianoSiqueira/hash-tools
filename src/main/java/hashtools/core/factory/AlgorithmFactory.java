package hashtools.core.factory;

public class AlgorithmFactory {

    public String getAlgorithm(int size) {
        switch (size) {
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
                throw new IllegalArgumentException("The given size does not fit any algorithm: '" + size + "'");
        }
    }

    public String getAlgorithm(String name) {
        if (name == null)
            throw new IllegalArgumentException("The name cannot be null");

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
                throw new IllegalArgumentException("The given name does not fit any algorithm: '" + name + "'");
        }
    }
}
