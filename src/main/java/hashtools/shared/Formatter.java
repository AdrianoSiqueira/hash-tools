package hashtools.shared;

public interface Formatter<INPUT_DATA> {

    static <INPUT_DATA> String format(INPUT_DATA inputData, Formatter<INPUT_DATA> formatter) {
        return formatter.format(inputData);
    }

    String format(INPUT_DATA input);
}
