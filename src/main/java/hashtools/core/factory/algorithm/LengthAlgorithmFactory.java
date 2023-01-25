package hashtools.core.factory.algorithm;

public class LengthAlgorithmFactory implements AlgorithmFactory {

    private final int length;

    public LengthAlgorithmFactory(int length) {
        this.length = length;
    }

    @Override
    public String create() {
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
}
