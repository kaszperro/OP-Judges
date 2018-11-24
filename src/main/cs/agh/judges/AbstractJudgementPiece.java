package cs.agh.judges;

import org.json.simple.JSONObject;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractJudgementPiece {
    public final List<Judgement> judgementList = new LinkedList<>();

    public void addJudgement(Judgement judgement) {
        judgementList.add(judgement);
    }


    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);
}
