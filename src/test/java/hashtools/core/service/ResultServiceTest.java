package hashtools.core.service;

import hashtools.core.model.Result;
import hashtools.core.model.Sample;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResultServiceTest {

    private ResultService service = new ResultService();


    private static List<Arguments> getExceptionTestsCalculateReliabilityPercentage() {
        return List.of(
                Arguments.of(NullPointerException.class, null)
        );
    }

    private static List<Arguments> getExceptionTestsCalculateResult() {
        return List.of(
                Arguments.of(NullPointerException.class, getSampleNull()),
                Arguments.of(NullPointerException.class, getSampleNullCalculated())
        );
    }

    private static List<Arguments> getResultTestsCalculateReliabilityPercentage() {
        return List.of(
                Arguments.of(0, List.of()),
                Arguments.of(75, getSamples75())
        );
    }

    private static List<Arguments> getResultTestsCalculateResult() {
        return List.of(
                Arguments.of(Result.SAFE, getSampleSafe()),
                Arguments.of(Result.UNSAFE, getSampleUnsafe())
        );
    }


    private static Sample getSampleNull() {
        return null;
    }

    private static Sample getSampleNullCalculated() {
        return new Sample();
    }

    private static Sample getSampleSafe() {
        Sample sample = new Sample();
        sample.setOfficialHash("123");
        sample.setCalculatedHash("123");

        return sample;
    }

    private static Sample getSampleUnsafe() {
        Sample sample = new Sample();
        sample.setOfficialHash("123");
        sample.setCalculatedHash("321");

        return sample;
    }

    private static List<Sample> getSamples75() {
        Sample s1 = new Sample();
        s1.setResult(Result.SAFE);

        Sample s2 = new Sample();
        s2.setResult(Result.SAFE);

        Sample s3 = new Sample();
        s3.setResult(Result.SAFE);

        Sample s4 = new Sample();
        s4.setResult(Result.UNSAFE);

        return List.of(s1, s2, s3, s4);
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTestsCalculateReliabilityPercentage")
    void calculateReliabilityPercentage(double result, List<Sample> samples) {
        assertEquals(result, service.calculateReliabilityPercentage(samples));
    }

    @ParameterizedTest
    @MethodSource(value = "getExceptionTestsCalculateReliabilityPercentage")
    void calculateReliabilityPercentage(Class<? extends Throwable> result, List<Sample> samples) {
        assertThrows(result, () -> service.calculateReliabilityPercentage(samples));
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTestsCalculateResult")
    void calculateResult(Result result, Sample sample) {
        assertEquals(result, service.calculateResult(sample));
    }

    @ParameterizedTest
    @MethodSource(value = "getExceptionTestsCalculateResult")
    void calculateResult(Class<? extends Throwable> result, Sample sample) {
        assertThrows(result, () -> service.calculateResult(sample));
    }
}
