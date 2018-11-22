package cs.agh.judges;

import org.json.simple.JSONObject;

public class CourtCase implements IJudgmentTracker {
    String caseNumber;

    CourtCase(JSONObject object) {
        caseNumber = (String) object.get("caseNumber");
    }

    CourtCase(String caseNumber) {
        this.caseNumber = caseNumber;
    }
}
