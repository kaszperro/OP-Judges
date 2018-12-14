package cs.agh.judges.judgementElements;


public enum JudgesSpecialRole {
    PRESIDING_JUDGE,
    REPORTING_JUDGE,
    REASONS_FOR_JUDGMENT_AUTHOR;

    @Override
    public String toString() {
        switch (this) {
            case PRESIDING_JUDGE:
                return "przewodniczacy składu sędziowskiego";
            case REPORTING_JUDGE:
                return " sędzia sprawozdawca";
            case REASONS_FOR_JUDGMENT_AUTHOR:
                return "autor uzasadnienia";
            default:
                return "";
        }
    }
}
