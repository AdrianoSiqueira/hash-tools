package hashtools.checker;

import hashtools.checker.condition.ChecksumFileSizeIsValidCondition;
import hashtools.checker.condition.ChecksumFileTypeIsValidCondition;
import hashtools.checker.exception.InvalidChecksumFileSizeException;
import hashtools.checker.exception.InvalidChecksumFileTypeException;
import hashtools.checker.exception.MissingChecksumFileException;
import hashtools.checker.exception.MissingInputFileException;
import hashtools.shared.Evaluation;
import hashtools.shared.condition.FileIsRegularFileCondition;
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
