package hashtools.service;

public interface Service<REQUEST_TYPE, RESPONSE_TYPE> {

    RESPONSE_TYPE run(REQUEST_TYPE requestType);
}
