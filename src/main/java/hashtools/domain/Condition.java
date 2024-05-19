package hashtools.domain;

public interface Condition<TYPE> {

    default boolean isFalse(TYPE input) {
        return !isTrue(input);
    }

    boolean isTrue(TYPE input);
}
