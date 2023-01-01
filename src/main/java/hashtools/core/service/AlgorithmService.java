package hashtools.core.service;

public class AlgorithmService {

    public boolean algorithmIsValid(String algorithm) {
        if (algorithm == null)
            return false;

        switch (algorithm) {
            case "MD5":
            case "SHA-512":
            case "SHA-384":
            case "SHA-256":
            case "SHA-224":
            case "SHA-1":
                return true;
            default:
                return false;
        }
    }
}
