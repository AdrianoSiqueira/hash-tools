package hashtools.core.factory;

import hashtools.core.formatter.RuntimeDataCheckFormatter;
import hashtools.core.formatter.RuntimeDataFormatter;
import hashtools.core.formatter.RuntimeDataGenerateFormatter;

public class RuntimeDataFormatterFactory {

    public RuntimeDataFormatter getFormatter(boolean checking) {
        return checking
               ? new RuntimeDataCheckFormatter()
               : new RuntimeDataGenerateFormatter();
    }
}
