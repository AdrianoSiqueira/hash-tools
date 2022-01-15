package hashtools.core.model;

import hashtools.core.language.LanguageManager;

/**
 * <p>It represents the possible results of the analysis.</p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public enum Result {

    /**
     * <p>Used when hashes do not match.</p>
     *
     * @since 1.0.0
     */
    DANGEROUS(0.0, "Dangerous"),

    /**
     * <p>Used when the hashes match perfectly.</p>
     *
     * @since 1.0.0
     */
    SAFE(1.0, "Safe"),

    /**
     * <p>Used when hashes match regardless of the case.</p>
     *
     * @since 1.0.0
     */
    UNSURE(0.5, "Unsure");


    private double score;
    private String text;


    Result(double score, String text) {
        this.score = score;
        this.text = text;
    }


    public double getScore() {
        return score;
    }

    public String getText() {
        return LanguageManager.get(text);
    }


    @Override
    public String toString() {
        return "Result{" +
               "score=" + score +
               ", text='" + text + '\'' +
               '}';
    }
}
