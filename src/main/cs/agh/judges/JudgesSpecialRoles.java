package cs.agh.judges;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum JudgesSpecialRoles {
    @JsonProperty("PRESIDING_JUDGE")
    PRESIDING_JUDGE,
    @JsonProperty("REPORTING_JUDGE")
    REPORTING_JUDGE,
    @JsonProperty("REASONS_FOR_JUDGMENT_AUTHOR")
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
