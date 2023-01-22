package hashtools.core.consumer.data;

import hashtools.core.model.Data;

public class CLIDataConsumer implements DataConsumer {

    @Override
    public void consume(Data data) {
        String result = data.getFormatter()
                            .format(data);

        System.out.println(result);
    }
}
