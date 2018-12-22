package cs.agh.judges.judgementElements;

import java.util.Objects;

public class CourtCase {
    String caseNumber;

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

}
