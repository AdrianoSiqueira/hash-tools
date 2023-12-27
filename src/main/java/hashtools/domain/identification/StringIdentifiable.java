package hashtools.domain.identification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringIdentifiable implements Identifiable {

    private String string;

    @Override
    public String identify() {
        return string;
    }
}
