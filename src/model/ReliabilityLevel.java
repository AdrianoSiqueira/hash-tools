package model;

public enum ReliabilityLevel {
    DANGEROUS(0.0, language.LanguageManager.get("Dangerous")),
    SAFE(1.0, language.LanguageManager.get("Safe")),
    UNSURE(0.5, language.LanguageManager.get("Unsure"));

    public final double score;
    public final String status;

    ReliabilityLevel(double score, String status) {
        this.score  = score;
        this.status = status;
    }

    public double getScore() {
        return score;
    }

    public String getStatus() {
        return status;
    }
}