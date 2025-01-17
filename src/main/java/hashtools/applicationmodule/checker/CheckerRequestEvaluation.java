package hashtools.applicationmodule.checker;

import hashtools.applicationmodule.checker.condition.ChecksumFileSizeIsValidCondition;
import hashtools.applicationmodule.checker.condition.ChecksumFileTypeIsValidCondition;
import hashtools.applicationmodule.checker.exception.InvalidChecksumFileSizeException;
import hashtools.applicationmodule.checker.exception.InvalidChecksumFileTypeException;
import hashtools.applicationmodule.checker.exception.MissingChecksumFileException;
import hashtools.applicationmodule.checker.exception.MissingInputFileException;
import hashtools.coremodule.Evaluation;
import hashtools.coremodule.condition.FileIsRegularFileCondition;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckerRequestEvaluation extends Evaluation {

    private final CheckerRequest request;

    @Override
    protected void evaluate() {
        evaluate(
            new FileIsRegularFileCondition(request.getInputFile()),
            new MissingInputFileException()
        );

        evaluate(
            new FileIsRegularFileCondition(request.getChecksumFile()),
            new MissingChecksumFileException()
        );

        evaluate(
            new ChecksumFileTypeIsValidCondition(request.getChecksumFile()),
            new InvalidChecksumFileTypeException()
        );

        evaluate(
            new ChecksumFileSizeIsValidCondition(request.getChecksumFile()),
            new InvalidChecksumFileSizeException()
        );
    }
}
