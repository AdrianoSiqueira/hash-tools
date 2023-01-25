package hashtools.core.runner;

import hashtools.core.model.Data;
import hashtools.core.operation.data.ConsumerDataOperation;
import hashtools.core.operation.data.DataOperation;
import hashtools.core.operation.data.HashGeneratorDataOperation;
import hashtools.core.operation.data.HashesLoaderDataOperation;
import hashtools.core.operation.data.SafetyCalculatorDataOperation;

public class CoreRunner implements Runner {

    private final Data data;

    public CoreRunner(Data data) {
        this.data = data;
    }

    private void performOperation(DataOperation operation, Data data) {
        operation.perform(data);
    }

    @Override
    public void run() {
        performOperation(new HashesLoaderDataOperation(), data);
        performOperation(new HashGeneratorDataOperation(), data);
        performOperation(new SafetyCalculatorDataOperation(), data);
        performOperation(new ConsumerDataOperation(), data);
    }
}
