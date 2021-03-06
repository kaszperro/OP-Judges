package cs.agh.judges.judgementElements;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Judgement {

    public CourtType courtType;
    public List<CourtCase> courtCases;
    public List<AbstractRegulation> referencedRegulations;
    public String textContent;
    public LocalDate judgmentDate;

    public final Map<Judge, JudgesSpecialRole[]> judgesRoles = new HashMap<>();


    public Judgement(
            CourtType courtType,
            List<CourtCase> courtCases,
            Map<Judge, JudgesSpecialRole[]> judgesRoles,
            List<AbstractRegulation> referencedRegulations,
            String textContent,
            LocalDate judgmentDate
    ) {

        this.courtType = courtType;
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
