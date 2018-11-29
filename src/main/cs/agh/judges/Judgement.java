package cs.agh.judges;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Judgement {

    public int id;
    public Court court;
    public List<CourtCase> courtCases;
    public List<Regulation> referencedRegulations;
    public String textContent;
    public Date judgmentDate;

    public final Map<Judge, JudgesSpecialRole[]> judgesRoles = new HashMap<>();


    public Judgement(
            int id,
            Court court,
            List<CourtCase> courtCases,
            Map<Judge, JudgesSpecialRole[]> judgesRoles,
            List<Regulation> referencedRegulations,
            String textContent,
            Date judgmentDate
    ) {
        this.id = id;
        this.court = court;
        this.courtCases = courtCases;
        for (Map.Entry<Judge, JudgesSpecialRole[]> mapEntry : judgesRoles.entrySet()) {
            this.judgesRoles.put(mapEntry.getKey(), mapEntry.getValue());
        }

        this.referencedRegulations = referencedRegulations;
        this.textContent = textContent;
        this.judgmentDate = judgmentDate;
    }

    public Judgement() {
    }


}
