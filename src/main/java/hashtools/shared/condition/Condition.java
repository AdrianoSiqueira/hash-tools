package hashtools.shared.condition;

import java.util.stream.Stream;

public interface Condition {

    static Condition allOf(Condition... conditions) {
        return () -> Stream
            .of(conditions)
            .allMatch(Condition::isTrue);
    }

    default boolean isFalse() {
        return !isTrue();
    }

    boolean isTrue();
}
