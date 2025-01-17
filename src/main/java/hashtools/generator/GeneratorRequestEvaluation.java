package hashtools.generator;

import hashtools.coremodule.Evaluation;
import hashtools.coremodule.condition.FileIsRegularFileCondition;
import hashtools.generator.condition.AlgorithmSelectionIsValidCondition;
import hashtools.generator.exception.InvalidAlgorithmSelectionException;
import hashtools.generator.exception.MissingInputFileException;
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
