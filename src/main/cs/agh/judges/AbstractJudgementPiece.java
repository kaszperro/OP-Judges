package cs.agh.judges;

import org.json.simple.JSONObject;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractJudgementPiece implements Comparable<AbstractJudgementPiece> {
    public final List<Judgement> judgementList = new LinkedList<>();

    public void addJudgement(Judgement judgement) {
        judgementList.add(judgement);
    }


    @Override
    public int compareTo(AbstractJudgementPiece o) {
        if (o.judgementList.size() < judgementList.size()) {
            return -1;
        } else if (o.judgementList.size() == judgementList.size())
            return 0;
        return 1;
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);
}
