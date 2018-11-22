package cs.agh.judges;

import java.util.LinkedList;
import java.util.List;

public interface IJudgmentTracker {
    List<Judgement> JUDGEMENT_LIST = new LinkedList<>();
    default void addJudgment(Judgement judgement){
        this.JUDGEMENT_LIST.add(judgement);
    }
}
