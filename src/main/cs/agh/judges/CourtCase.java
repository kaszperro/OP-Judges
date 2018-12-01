package cs.agh.judges;

import org.json.simple.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CourtCase implements IJudgementElement {
    String caseNumber;

    private final List<Judgement> judgementsList = new LinkedList<>();

    CourtCase(JSONObject object) {
        caseNumber = (String) object.get("caseNumber");
    }

    public CourtCase(String caseNumber) {
        this.caseNumber = caseNumber;
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

    @Override
    public List<Judgement> getJudgementList() {
        return judgementsList;
    }
}
