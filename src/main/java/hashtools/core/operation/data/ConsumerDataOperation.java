package hashtools.core.operation.data;

import hashtools.core.model.Data;

/**
 * <p>
 * Executes all consumers within the {@link Data} object.
 * </p>
 */
public class ConsumerDataOperation implements DataOperation {

    @Override
    public void perform(Data data) {
        data.getConsumers()
            .forEach(consumer -> consumer.consume(data));
    }
}
