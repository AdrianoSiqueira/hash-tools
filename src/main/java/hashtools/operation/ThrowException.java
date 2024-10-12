package hashtools.operation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ThrowException implements Operation {

    private final RuntimeException exception;

    @Override
    public void perform() {
        throw exception;
    }
}
