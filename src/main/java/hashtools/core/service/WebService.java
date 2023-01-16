package hashtools.core.service;

import javafx.application.HostServices;

public class WebService {

    private HostServices service;

    public WebService(HostServices service) {
        this.service = service;
    }

    public HostServices getHostServices() {
        return service;
    }

    public void openWebPage(String url) {
        service.showDocument(url);
    }
}
