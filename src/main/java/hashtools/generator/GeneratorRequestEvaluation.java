package hashtools.generator;

import hashtools.generator.condition.AlgorithmSelectionIsValidCondition;
import hashtools.generator.exception.InvalidAlgorithmSelectionException;
import hashtools.generator.exception.MissingInputFileException;
import hashtools.shared.Evaluation;
import hashtools.shared.condition.FileIsRegularFileCondition;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GeneratorRequestEvaluation extends Evaluation {

    private final GeneratorRequest request;

    @Override
    protected void evaluate() {
        evaluate(
            new FileIsRegularFileCondition(request.getInputFile()),
            new MissingInputFileException()
        );

        evaluate(
            new AlgorithmSelectionIsValidCondition(request.getAlgorithms()),
            new InvalidAlgorithmSelectionException()
        );
    }
}
