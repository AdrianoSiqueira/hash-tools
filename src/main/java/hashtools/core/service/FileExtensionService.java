package hashtools.core.service;

import hashtools.core.model.FileExtension;

import java.util.Optional;
import java.util.stream.Stream;

@Deprecated
public class FileExtensionService {

    @Deprecated
    public boolean extensionIsValid(String extension, FileExtension fileExtension) {
        if (fileExtension == null)
            throw new NullPointerException("fileExtension cannot be null");

        if (fileExtension == FileExtension.ALL) return true;

        String search = "*" + Optional.ofNullable(extension)
                                      .map(s -> s.replaceAll("[*.]", ""))
                                      .map(s -> s.isBlank() ? s : "." + s)
                                      .orElse("");

        return Stream.of(fileExtension.getExtensions())
                     .anyMatch(s -> s.equalsIgnoreCase(search));
    }
}
