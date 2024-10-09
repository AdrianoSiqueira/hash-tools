package hashtools.condition;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObjectIsNotNull implements Condition {

    private final Object object;

    @Override
    public boolean isTrue() {
        return object != null;
    }
}
