package cs.agh.judges;

import java.util.LinkedList;
import java.util.List;

public enum CourtType implements IJudgementElement {
    COMMON,
    SUPREME,
    ADMINISTRATIVE,
    CONSTITUTIONAL_TRIBUNAL,
    NATIONAL_APPEAL_CHAMBER;

    private final List<Judgement> judgementsList = new LinkedList<>();

    @Override
    public String toString() {
        switch (this) {
            case COMMON:
                return "Sąd Powszechny";
            case SUPREME:
                return "Sąd Najwyższy";
            case ADMINISTRATIVE:
                return "Sąd Administracyjny";
            case CONSTITUTIONAL_TRIBUNAL:
                return "Trybunał Konstytucyjny";
            case NATIONAL_APPEAL_CHAMBER:
                return "Krajowa Izba Odwoławcza";
            default:
                return "";
        }
    }

    @Override
    public List<Judgement> getJudgementList() {
        return judgementsList;
    }
}

