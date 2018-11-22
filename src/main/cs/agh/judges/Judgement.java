package cs.agh.judges;

import java.util.Date;

public class Judgement {

    int id;
    CourtType courtType;
    CourtCase[] courtCases;
    JudgmentType judgmentType;
    Judge[] judges;
    Regulation[] referencedRegulations;
    Date judgmentDate;

    public Judgement(
            int id,
            CourtType courtType,
            CourtCase[] courtCases,
            JudgmentType judgmentType,
            Judge[] judges,
            Regulation[] referencedRegulations,
            Date judgmentDate
    ) {
        this.id = id;
        this.courtType = courtType;
        this.courtCases = courtCases;
        this.judgmentType = judgmentType;
        this.judges = judges;
        this.referencedRegulations = referencedRegulations;
        this.judgmentDate = judgmentDate;
    }
}
