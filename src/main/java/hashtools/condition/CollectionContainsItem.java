package hashtools.condition;

import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class CollectionContainsItem<TYPE> implements Condition {

    private final Collection<TYPE> collection;
    private final TYPE item;

    @Override
    public boolean isTrue() {
        return collection.contains(item);
    }
}
