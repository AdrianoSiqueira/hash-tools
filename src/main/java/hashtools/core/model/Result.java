package hashtools.core.model;

import lombok.Getter;

@Getter
public enum Result {

    SAFE("Safe"),
    UNSAFE("Unsafe");


    private String text;


    Result(String text) {
        this.text = text;
    }
}
