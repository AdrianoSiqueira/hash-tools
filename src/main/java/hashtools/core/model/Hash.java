package hashtools.core.model;

public class Hash {

    private String official;
    private String generated;

    public Hash() {
    }

    public String getGenerated() {
        return generated;
    }

    public String getOfficial() {
        return official;
    }

    public boolean matches() {
        if (official == null) return false;
        if (generated == null) return false;

        return official.equalsIgnoreCase(generated);
    }

    public void setGenerated(String generated) {
        this.generated = generated;
    }

    public void setOfficial(String official) {
        this.official = official;
    }
}
