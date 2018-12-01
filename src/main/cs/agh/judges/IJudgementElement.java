package cs.agh.judges;


import java.util.List;

public interface IJudgementElement {
    boolean equals(Object o);

    int hashCode();

    List<Judgement> getJudgementList();

    default void addJudgement(Judgement judgement) {
        getJudgementList().add(judgement);
    }
}
