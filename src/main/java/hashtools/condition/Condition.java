package hashtools.condition;

import java.util.Collection;

public interface Condition {

    static Condition allOf(Collection<Condition> conditions) {
        return () -> conditions
            .stream()
            .allMatch(Condition::isTrue);
    }

    default boolean isFalse() {
        return !isTrue();
    }

    boolean isTrue();
}
