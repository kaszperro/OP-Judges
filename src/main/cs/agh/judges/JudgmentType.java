package cs.agh.judges;

public enum JudgmentType implements IJudgmentTracker {
    DECISION,
    RESOLUTION,
    SENTENCE,
    REGULATION,
    REASONS;

    @Override
    public String toString() {
        switch (this) {
            case DECISION:
                return "postanowienie";
            case RESOLUTION:
                return "uchwała";
            case SENTENCE:
                return "wyrok";
            case REGULATION:
                return "zarządzenie";
            case REASONS:
                return "uzasadnienie";
            default:
                return "";
        }
    }
}
