package hashtools.core.service;

import hashtools.core.model.FileExtension;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileExtensionServiceTest {

    private FileExtensionService service = new FileExtensionService();


    @Test
    void extensionIsValid_returnFalseWhenExtensionIsNotValid() {
        assertFalse(service.extensionIsValid("", FileExtension.HASH));
    }

    @Test
    void extensionIsValid_returnTrueWhenExtensionIsValid() {
        assertTrue(service.extensionIsValid("", FileExtension.ALL), "'ALL' failed");
        assertTrue(service.extensionIsValid("jpg", FileExtension.ALL), "'ALL (jpg)' failed");
        assertTrue(service.extensionIsValid(".md5", FileExtension.HASH), "'HASH' failed");
        assertTrue(service.extensionIsValid("md5", FileExtension.HASH), "'HASH (without dot)' failed");
    }

    @Test
    void extensionIsValid_returnVariesWhenExtensionIsNull() {
        assertFalse(service.extensionIsValid(null, FileExtension.HASH), "'HASH' failed");
        assertTrue(service.extensionIsValid(null, FileExtension.ALL), "'ALL' failed");
    }
}
