package hashtools.core.service;

import hashtools.core.model.Result;
import hashtools.core.model.Sample;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResultServiceTest {

    private ResultService service = new ResultService();

    @Test
    void calculateReliabilityPercentage_calculatePercentageWhenListIsProvided() {
        Sample s1 = new Sample();
        s1.setResult(Result.SAFE);

        Sample s2 = new Sample();
        s2.setResult(Result.SAFE);

        Sample s3 = new Sample();
        s3.setResult(Result.SAFE);

        Sample s4 = new Sample();
        s4.setResult(Result.UNSAFE);

        assertEquals(75, service.calculateReliabilityPercentage(List.of(s1, s2, s3, s4)));
    }

    @Test
    void calculateReliabilityPercentage_returnZeroWhenListIsEmpty() {
        assertEquals(0, service.calculateReliabilityPercentage(List.of()));
    }

    @Test
    void calculateReliabilityPercentage_throwsExceptionWhenListIsNull() {
        assertThrows(NullPointerException.class, () -> service.calculateReliabilityPercentage(null));
    }


    @Test
    void calculateResult_returnSafeWhenHashesMatches() {
        Sample sample = new Sample();
        sample.setOfficialHash("123");
        sample.setCalculatedHash("123");

        assertEquals(Result.SAFE, service.calculateResult(sample));
    }

    @Test
    void calculateResult_returnUnsafeWhenHashesDoesNotMatches() {
        Sample sample = new Sample();
        sample.setOfficialHash("123");
        sample.setCalculatedHash("");

        assertEquals(Result.UNSAFE, service.calculateResult(sample));
    }

    @Test
    void calculateResult_throwsExceptionWhenCalculatedHashIsNull() {
        Sample s1 = new Sample();
        s1.setOfficialHash("");
        s1.setCalculatedHash(null);

        assertThrows(NullPointerException.class, () -> service.calculateResult(s1));
    }

    @Test
    void calculateResult_throwsExceptionWhenSampleIsNull() {
        assertThrows(NullPointerException.class, () -> service.calculateResult(null));
    }
}
