package hashtools.core.factory;

import hashtools.core.formatter.Formatter;
import hashtools.core.formatter.RuntimeDataCheckFormatter;
import hashtools.core.formatter.RuntimeDataGenerateFormatter;
import hashtools.core.model.RuntimeData;

public class RuntimeDataFormatterFactory {

    public Formatter<RuntimeData> getFormatter(boolean checking) {
        return checking
               ? new RuntimeDataCheckFormatter()
               : new RuntimeDataGenerateFormatter();
    }
}
