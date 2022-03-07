package hashtools.core.model;

import hashtools.core.service.HashAlgorithmService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnvironmentTest {

    @Test
    void getAlgorithms_returnEmptyListWhenNoValidHasIsProvided() {
        Environment environment = new Environment();
        environment.setAlgorithms(new HashAlgorithmService().convertToAlgorithmList("md", ""));

        assertTrue(environment.getAlgorithms().isEmpty());
    }

    @Test
    void getAlgorithms_returnFilledListWhenValidHasIsProvided() {
        Environment environment = new Environment();
        environment.setAlgorithms(new HashAlgorithmService().convertToAlgorithmList("md5", "sha-----1"));

        assertFalse(environment.getAlgorithms().isEmpty());
    }


    @Test
    void getOfficialData_returnSameResultOfOutputData() {
        Environment environment = new Environment();
        environment.setOfficialData("123");

        assertEquals(environment.getOutputData(), environment.getOfficialData());
    }
}
