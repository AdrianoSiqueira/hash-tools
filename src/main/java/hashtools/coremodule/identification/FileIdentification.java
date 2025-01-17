package hashtools.coremodule.identification;

import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

@RequiredArgsConstructor
public class FileIdentification implements Identification {

    private final Path file;

    @Override
    public String identity() {
        return file
            .getFileName()
            .toString();
    }
}
