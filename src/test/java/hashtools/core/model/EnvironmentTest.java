package hashtools.core.model;

import hashtools.core.service.HashAlgorithmService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvironmentTest {

    private HashAlgorithmService service = new HashAlgorithmService();


    private static List<Arguments> getResultTestsGetAlgorithms() {
        return List.of(
                Arguments.of(new String[]{"", "md"}, true),
                Arguments.of(new String[]{"md5", "sha---1"}, false)
        );
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTestsGetAlgorithms")
    void getAlgorithms(String[] algorithms, boolean result) {
        Environment environment = new Environment();
        environment.setAlgorithms(service.convertToAlgorithmList(algorithms));

        assertEquals(result, environment.getAlgorithms().isEmpty());
    }


    @Test
    @DisplayName(value = "OutputData and OfficialData are equals")
    void getOfficialData() {
        Environment environment = new Environment();
        environment.setOfficialData("123");

        assertEquals(environment.getOutputData(), environment.getOfficialData());
    }
}
