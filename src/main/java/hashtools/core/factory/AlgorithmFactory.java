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
                throw new IllegalArgumentException("The given size does not fit any algorithm");
        }
    }
}
