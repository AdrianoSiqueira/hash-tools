package hashtools.shared;

public interface ResponseFormatter<RESPONSE_TYPE> {

    String formatResponse(RESPONSE_TYPE responseType, Formatter<RESPONSE_TYPE> formatter);
}
