package cs.agh.judges;

import org.json.simple.JSONObject;

import java.util.Objects;

public class CourtCase extends AbstractJudgementPiece {
    String caseNumber;

    CourtCase(JSONObject object) {
        caseNumber = (String) object.get("caseNumber");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourtCase courtCase = (CourtCase) o;
        return Objects.equals(caseNumber, courtCase.caseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseNumber);
    }
}
