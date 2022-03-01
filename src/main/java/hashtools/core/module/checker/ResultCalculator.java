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
                    String official   = s.getOfficialHash();
                    String calculated = s.getCalculatedHash();

                    if (official.equals(calculated)) {
                        s.setResult(Result.SAFE);
                    } else if (official.equalsIgnoreCase(calculated)) {
                        s.setResult(Result.UNSURE);
                    } else {
                        s.setResult(Result.DANGEROUS);
                    }
                });
    }
}
