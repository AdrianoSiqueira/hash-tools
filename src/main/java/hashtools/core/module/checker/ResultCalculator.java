package hashtools.core.module.checker;

import hashtools.core.model.Result;
import hashtools.core.model.Sample;

import java.util.Optional;
import java.util.function.Consumer;

public class ResultCalculator implements Consumer<Sample> {

@Override
    public void accept(Sample sample) {
        Optional.ofNullable(sample)
                .ifPresent(s -> {
                    Result result = s.getOfficialHash().equalsIgnoreCase(s.getCalculatedHash())
                                    ? Result.SAFE
                                    : Result.UNSAFE;

                    s.setResult(result);
                });
    }
}
