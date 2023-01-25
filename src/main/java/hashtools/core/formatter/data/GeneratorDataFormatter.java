package hashtools.core.formatter.data;

import hashtools.core.model.Data;

import java.util.StringJoiner;

public class GeneratorDataFormatter extends DataFormatter {

    @Override
    public String format(Data data) {
        if (data == null)
            return null;

        String name = data.isUsingInputFile()
                      ? data.getInputFile().getFileName().toString()
                      : data.getInputText();

        StringJoiner result = new StringJoiner("\n");

        sortHashes(data.getHashes()).forEach(hash -> {
            String content = hash.getGenerated() + "  \"" + name + '\"';

            result.add(content);
        });

        return result.toString();
    }
}
