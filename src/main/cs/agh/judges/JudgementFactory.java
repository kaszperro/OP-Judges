package cs.agh.judges;

import java.util.*;

public class JudgementFactory {
    public Set<Judgement> judgements = new HashSet<>();
    public Map<AbstractJudgementPiece, AbstractJudgementPiece> judgementPieces = new HashMap<>();


    public void addJudgement(Judgement judgement) {
        judgements.add(judgement);
    }

    public <T extends AbstractJudgementPiece> void addPiecesToJudgement(List<T> piecesToAdd, Judgement judgement) {
        for (AbstractJudgementPiece piece : piecesToAdd) {
            judgementPieces.put(piece, piece);
            piece.addJudgement(judgement);
        }
    }


    public <T extends AbstractJudgementPiece> List<T> removeDuplicats(List<T> toRemove) {
        List<T> returnList = new LinkedList<>();
        for (T myObject : toRemove) {
            returnList.add((T) judgementPieces.getOrDefault(myObject, myObject));
        }
        return returnList;
    }


    public <T extends AbstractJudgementPiece> List<T> getPiecesOfType(Class<T> myClass) {
        List<T> returnList = new LinkedList<>();

        for (AbstractJudgementPiece judgementPiece : judgementPieces.keySet()) {
            if (myClass.isInstance(judgementPiece)) {
                returnList.add((T) judgementPiece);
            }
        }
        return returnList;
    }


}
