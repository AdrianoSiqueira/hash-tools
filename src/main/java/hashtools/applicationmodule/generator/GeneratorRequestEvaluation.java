package hashtools.applicationmodule.generator;

import hashtools.coremodule.Evaluation;
import hashtools.coremodule.condition.FileIsRegularFileCondition;
import hashtools.applicationmodule.generator.condition.AlgorithmSelectionIsValidCondition;
import hashtools.applicationmodule.generator.exception.InvalidAlgorithmSelectionException;
import hashtools.applicationmodule.generator.exception.MissingInputFileException;
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
