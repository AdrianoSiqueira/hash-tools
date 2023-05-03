package hashtools.core.runner;

import hashtools.core.consumer.data.CLIDataConsumer;
import hashtools.core.factory.data.ArgumentDataFactory;
import hashtools.core.factory.data.DataFactory;
import hashtools.core.model.Data;

import java.util.List;

/**
 * <p>
 * Command line running mode. It creates the {@link Data} object from
 * the arguments and passes it to the {@link CoreRunner}.
 * </p>
 */
public class CLIRunner implements Runner {

    private final DataFactory factory;

    public CLIRunner(String[] arguments) {
        this.factory = new ArgumentDataFactory(List.of(arguments));
    }

    @Override
    public void run() {
        Data data = factory.create();
        data.setConsumers(new CLIDataConsumer());

        new CoreRunner(data).run();
    }
}
