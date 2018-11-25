package cs.agh.judges;

import java.util.*;

public class JudgementFactory {
    public Set<Judgement> judgements = new HashSet<>();
    public Map<AbstractJudgementPiece, AbstractJudgementPiece> judgementPieces = new HashMap<>();


    public void addJudgement(Judgement judgement) {
        judgements.add(judgement);
    }

    public <T extends AbstractJudgementPiece> void addPiecesToJudgement(T pieceToAdd, Judgement judgement) {
        judgementPieces.put(pieceToAdd, pieceToAdd);
        pieceToAdd.addJudgement(judgement);
    }

    public <T extends AbstractJudgementPiece> void addPiecesToJudgement(List<T> piecesToAdd, Judgement judgement) {
        for (AbstractJudgementPiece piece : piecesToAdd) {
            addPiecesToJudgement(piece, judgement);
        }
    }


    public <T extends AbstractJudgementPiece> List<T> removeDuplicates(List<T> toRemove) {
        List<T> returnList = new LinkedList<>();
        for (T myObject : toRemove) {
            returnList.add((T) judgementPieces.getOrDefault(myObject, myObject));
        }
        return returnList;
    }

    public <T extends AbstractJudgementPiece> T removeDuplicates(T toRemove) {
        return (T) judgementPieces.getOrDefault(toRemove, toRemove);
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

    public <T extends AbstractJudgementPiece> T findPiece(T object) {
        return (T) judgementPieces.getOrDefault(object, null);
    }


}
