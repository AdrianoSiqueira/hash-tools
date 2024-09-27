package hashtools.condition;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScreenIndexIsOutOfBounds implements Condition {

    private final int firstIndex;
    private final int lastIndex;
    private final int currentIndex;

    @Override
    public boolean isTrue() {
        return currentIndex < firstIndex
            || currentIndex > lastIndex;
    }
}
