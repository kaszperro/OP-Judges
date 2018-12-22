package cs.agh.judges.judgementElements;

import java.util.Objects;

public class JSONRegulation extends AbstractRegulation {
    public String journalTitle;
    public int journalNo;
    public int journalYear;
    public int journalEntry;

    @Override
    public String getTitle() {
        return journalTitle;
    }

    public JSONRegulation(int journalYear, int journalEntry, int journalNo, String journalTitle) {
        this.journalYear = journalYear;
        this.journalEntry = journalEntry;
        this.journalNo = journalNo;
        this.journalTitle = journalTitle;
    }


    @Override
    public boolean compareSame(AbstractRegulation other) {
        JSONRegulation otherRegulation = (JSONRegulation) other;
        return journalNo == otherRegulation.journalNo &&
                journalYear == otherRegulation.journalYear &&
                journalEntry == otherRegulation.journalEntry;
    }

    @Override
    public int hashCode() {
        return Objects.hash(journalNo, journalYear, journalEntry);
    }


    @Override
    public String toString() {
        return journalTitle;
    }
}
