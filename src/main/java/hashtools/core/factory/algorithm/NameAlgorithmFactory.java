package hashtools.core.factory.algorithm;

public class NameAlgorithmFactory implements AlgorithmFactory {

    private final String name;

    public NameAlgorithmFactory(String name) {
        this.name = name;
    }

    @Override
    public String create() {
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
