package hashtools.shared.operation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ThrowException extends Operation {

    private final RuntimeException exception;

    @Override
    protected void perform() {
        throw exception;
    }
}
