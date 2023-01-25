package hashtools.core.factory.data;

import hashtools.core.model.Data;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArgumentDataFactoryTest {

    private static Data emptyData;
    private static Data checkTextWithHashData;
    private static Data checkTextWithFileData;
    private static Data checkFileWithHashData;
    private static Data checkFileWithFileData;
    private static Data generateTextData;
    private static Data generateFileData;

    private static Path officialFile;

    private DataFactory factory;

    @BeforeAll
    static void createData()
    throws IOException {
        officialFile = Path.of("official.txt");
        Files.createFile(officialFile);

        emptyData = new Data();

        checkTextWithHashData = new Data();
        checkTextWithHashData.setChecking();
        checkTextWithHashData.setInputText("");
        checkTextWithHashData.setOfficialHash("");
        checkTextWithHashData.setOutputFile(Path.of("output.txt"));

        checkTextWithFileData = new Data();
        checkTextWithFileData.setChecking();
        checkTextWithFileData.setInputText("");
        checkTextWithFileData.setOfficialFile(officialFile);
        checkTextWithFileData.setOutputFile(Path.of("output.txt"));

        checkFileWithHashData = new Data();
        checkFileWithHashData.setChecking();
        checkFileWithHashData.setInputFile(Path.of("input.txt"));
        checkFileWithHashData.setOfficialHash("");
        checkFileWithHashData.setOutputFile(Path.of("output.txt"));

        checkFileWithFileData = new Data();
        checkFileWithFileData.setChecking();
        checkFileWithFileData.setInputFile(Path.of("input.txt"));
        checkFileWithFileData.setOfficialFile(officialFile);
        checkFileWithFileData.setOutputFile(Path.of("output.txt"));

        generateTextData = new Data();
        generateTextData.setGenerating();
        generateTextData.setInputText("");
        generateTextData.setAlgorithms("md5", "sha1");
        generateTextData.setOutputFile(Path.of("output.txt"));

        generateFileData = new Data();
        generateFileData.setGenerating();
        generateFileData.setInputFile(Path.of("input.txt"));
        generateFileData.setAlgorithms("md5", "sha1");
        generateFileData.setOutputFile(Path.of("output.txt"));
    }

    private static List<Arguments> getArgumentTests() {
        List<String> emptyArguments             = List.of();
        List<String> checkTextWithHashArguments = List.of("--check-text=", "--with-hash=", "--output-file=output.txt");
        List<String> checkTextWithFileArguments = List.of("--check-text=", "--with-file=official.txt", "--output-file=output.txt");
        List<String> checkFileWithHashArguments = List.of("--check-file=input.txt", "--with-hash=", "--output-file=output.txt");
        List<String> checkFileWithFileArguments = List.of("--check-file=input.txt", "--with-file=official.txt", "--output-file=output.txt");
        List<String> generateTextArguments      = List.of("--generate-text=", "--algorithms=md5 sha1", "--output-file=output.txt");
        List<String> generateFileArguments      = List.of("--generate-file=input.txt", "--algorithms=md5 sha1", "--output-file=output.txt");

        return List.of(
                Arguments.of(null, emptyData),
                Arguments.of(emptyArguments, emptyData),
                Arguments.of(checkTextWithHashArguments, checkTextWithHashData),
                Arguments.of(checkTextWithFileArguments, checkTextWithFileData),
                Arguments.of(checkFileWithHashArguments, checkFileWithHashData),
                Arguments.of(checkFileWithFileArguments, checkFileWithFileData),
                Arguments.of(generateTextArguments, generateTextData),
                Arguments.of(generateFileArguments, generateFileData)
        );
    }

    @AfterAll
    static void removeData()
    throws IOException {
        Files.deleteIfExists(officialFile);
    }

    @ParameterizedTest
    @MethodSource(value = "getArgumentTests")
    @DisplayName(value = "Creates a data object from the given arguments")
    void create(List<String> arguments, Data expected) {
        factory = new ArgumentDataFactory(arguments);

        assertEquals(expected, factory.create());
    }
}
