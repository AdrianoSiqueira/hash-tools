package hashtools.coremodule.identification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringIdentification implements Identification {

    private final String string;

    @Override
    public String identity() {
        return string;
    }
}
