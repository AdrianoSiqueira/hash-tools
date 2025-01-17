package hashtools.coremodule.condition;

public abstract class Condition {

    public final boolean isFalse() {
        return !isTrue();
    }

    public abstract boolean isTrue();
}
