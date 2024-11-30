package hashtools.comparator;

import hashtools.comparator.exception.MissingInputFile1Exception;
import hashtools.comparator.exception.MissingInputFile2Exception;
import hashtools.shared.Evaluation;
import hashtools.shared.condition.FileIsRegularFileCondition;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ComparatorRequestEvaluation extends Evaluation {

    private final ComparatorRequest request;

    @Override
    protected void evaluate() {
        evaluate(
            new FileIsRegularFileCondition(request.getInputFile1()),
            new MissingInputFile1Exception()
        );

        evaluate(
            new FileIsRegularFileCondition(request.getInputFile2()),
            new MissingInputFile2Exception()
        );
    }
}
