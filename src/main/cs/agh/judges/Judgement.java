package cs.agh.judges;

import java.util.Date;
import java.util.List;

public class Judgement {

    int id;
    Court court;
    List<CourtCase> courtCases;
    List<Judge> judges;
    List<Regulation> referencedRegulations;
    Date judgmentDate;

    public Judgement(int id, Court court, List<CourtCase> courtCases, List<Judge> judges, List<Regulation> referencedRegulations, Date judgmentDate) {
        this.id = id;
        this.court = court;
        this.courtCases = courtCases;
        this.judges = judges;
        this.referencedRegulations = referencedRegulations;
        this.judgmentDate = judgmentDate;
    }

    public Judgement() {
    }
}
