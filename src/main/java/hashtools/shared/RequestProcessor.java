package hashtools.shared;

@Deprecated(forRemoval = true)
public interface RequestProcessor<REQUEST_TYPE, RESPONSE_TYPE> {

    RESPONSE_TYPE processRequest(REQUEST_TYPE requestType);
}
