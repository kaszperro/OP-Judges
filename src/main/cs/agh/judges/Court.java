package cs.agh.judges;

import org.json.simple.JSONObject;

import java.util.Objects;

public class Court extends AbstractJudgementPiece {
    public final CourtType courtType;

    Court(JSONObject object) {
        courtType = CourtType.valueOf((String) object.get("courtType"));
    }

    public Court(CourtType courtType) {
        this.courtType = courtType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Court court = (Court) o;
        return courtType == court.courtType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courtType);
    }
}
