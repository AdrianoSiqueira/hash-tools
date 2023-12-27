package hashtools.domain.identification;

import lombok.AllArgsConstructor;

import java.nio.file.Path;

@AllArgsConstructor
public class FileIdentifiable implements Identifiable {

    private Path file;

    @Override
    public String identify() {
        return file
            .getFileName()
            .toString();
    }
}
