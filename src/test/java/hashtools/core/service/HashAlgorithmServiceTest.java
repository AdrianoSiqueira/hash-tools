package hashtools.core.service;

import hashtools.core.model.HashAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashAlgorithmServiceTest {
    
    private HashAlgorithmService service = new HashAlgorithmService();
    
    
    @Test
    void getByLength_returnResultWhenLengthIsFound() {
        assertEquals(HashAlgorithm.MD5, service.getByLength(32), "'MD5' failed");
        assertEquals(HashAlgorithm.SHA1, service.getByLength(40), "'SHA-1' failed");
        assertEquals(HashAlgorithm.SHA224, service.getByLength(56), "'SHA-224' failed");
        assertEquals(HashAlgorithm.SHA256, service.getByLength(64), "'SHA-256' failed");
        assertEquals(HashAlgorithm.SHA384, service.getByLength(96), "'SHA-384' failed");
        assertEquals(HashAlgorithm.SHA512, service.getByLength(128), "'SHA-512' failed");
    }

    @Test
    void getByLength_throwExceptionWhenLengthIsNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.getByLength(0));
    }


    @Test
    void getByName_returnResultWhenNameIsFound() {
        assertEquals(HashAlgorithm.MD5, service.getByName("md5"), "'MD5' failed");
        assertEquals(HashAlgorithm.SHA1, service.getByName("SHA1"), "'SHA-1' failed");
        assertEquals(HashAlgorithm.SHA224, service.getByName("SHA-224"), "'SHA-224' failed");
        assertEquals(HashAlgorithm.SHA256, service.getByName("SHA-256"), "'SHA-256' failed");
        assertEquals(HashAlgorithm.SHA384, service.getByName("SHA-384"), "'SHA-384' failed");
        assertEquals(HashAlgorithm.SHA512, service.getByName("SHA----512"), "'SHA-512' failed");
    }

    @Test
    void getByName_throwExceptionWhenNameIsNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.getByName(""), "'Not found' failed");
        assertThrows(NoSuchElementException.class, () -> service.getByName(null), "'Null' failed");
    }


    @Test
    void searchByLength_returnResultWhenLengthIsFound() {
        assertTrue(service.searchByLength(32).isPresent(), "'MD5' failed");
        assertTrue(service.searchByLength(40).isPresent(), "'SHA-1' failed");
        assertTrue(service.searchByLength(56).isPresent(), "'SHA-224' failed");
        assertTrue(service.searchByLength(64).isPresent(), "'SHA-256' failed");
        assertTrue(service.searchByLength(96).isPresent(), "'SHA-384' failed");
        assertTrue(service.searchByLength(128).isPresent(), "'SHA-512' failed");
    }

    @Test
    void searchByLength_throwExceptionWhenLengthIsNotFound() {
        assertTrue(service.searchByLength(0).isEmpty());
    }


    @Test
    void searchByName_returnResultWhenNameIsFound() {
        assertTrue(service.searchByName("md5").isPresent(), "'MD5' failed");
        assertTrue(service.searchByName("SHA1").isPresent(), "'SHA-1' failed");
        assertTrue(service.searchByName("SHA-224").isPresent(), "'SHA-224' failed");
        assertTrue(service.searchByName("SHA-256").isPresent(), "'SHA-256' failed");
        assertTrue(service.searchByName("SHA-384").isPresent(), "'SHA-384' failed");
        assertTrue(service.searchByName("SHA----512").isPresent(), "'SHA-512' failed");
    }

    @Test
    void searchByName_throwExceptionWhenNameIsNotFound() {
        assertTrue(service.searchByName("").isEmpty(), "'Not found' failed");
        assertTrue(service.searchByName(null).isEmpty(), "'Null' failed");
    }


    @Test
    void stringHasValidLength_returnFalseWhenStringHasNotValidLength() {
        assertFalse(service.stringHasValidLength(""), "'MD5' failed");
    }

    @Test
    void stringHasValidLength_returnFalseWhenStringIsNull() {
        assertFalse(service.stringHasValidLength(null), "'MD5' failed");
    }

    @Test
    void stringHasValidLength_returnTrueWhenStringHasValidLength() {
        assertTrue(service.stringHasValidLength("11111111111111111111111111111111"), "'MD5' failed");
    }
}
