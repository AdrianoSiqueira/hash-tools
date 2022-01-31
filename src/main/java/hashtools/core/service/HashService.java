package hashtools.core.service;

import aslib.security.SHAType;

public class HashService {

    public static boolean stringHasValidLength(String string) {
        return SHAType.getByLength(string.length())
                      .isPresent();
    }
}
