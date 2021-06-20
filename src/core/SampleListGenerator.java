package core;

import aslib.filemanager.FileReader;
import aslib.security.SHAType;
import model.Sample;
import model.SampleBuilder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SampleListGenerator {

    public static List<Sample> generateFromFile(Path path) {
        return new FileReader()
                .readLines(path)
                .map(lines -> lines.stream()
                                   .map(SampleListGenerator::splitLineIntoWords)
                                   .flatMap(Stream::of)
                                   .filter(SampleListGenerator::isHashValid)
                                   .map(SampleListGenerator::createSampleFromHash)
                                   .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    public static List<Sample> generateFromSelection(SHAType... shaTypes) {
        return Arrays.stream(shaTypes)
                     .map(SampleListGenerator::createSampleFromShaType)
                     .collect(Collectors.toList());
    }


    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static Sample createSampleFromHash(String hash) {
        return new SampleBuilder()
                .shaType(SHAType.getByLength(hash.length()).get())
                .officialHash(hash)
                .create();
    }

    private static Sample createSampleFromShaType(SHAType shaType) {
        return new SampleBuilder()
                .shaType(shaType)
                .create();
    }

    private static boolean isHashValid(String hash) {
        return SHAType.getByLength(hash.length())
                      .isPresent();
    }

    private static String[] splitLineIntoWords(String line) {
        return line.split(" ");
    }
}
