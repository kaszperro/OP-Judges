package cs.agh.judges;

import org.json.simple.JSONObject;

import java.util.Objects;

import static java.lang.Math.toIntExact;

public class Regulation extends AbstractJudgementPiece {

    public String journalTitle;
    public int journalNo;
    public int journalYear;
    public int journalEntry;
    public String text;

    public Regulation(JSONObject object) {
        journalTitle = (String) object.get("journalTitle");
        journalNo = toIntExact((Long) object.get("journalNo"));
        journalYear = toIntExact((Long) object.get("journalYear"));
        journalEntry =  toIntExact((Long) object.get("journalEntry"));
        text = (String) object.get("text");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Regulation that = (Regulation) o;
        return journalNo == that.journalNo &&
                journalYear == that.journalYear &&
                journalEntry == that.journalEntry;
    }

    @Override
    public int hashCode() {
        return Objects.hash(journalNo, journalYear, journalEntry);
    }
}
