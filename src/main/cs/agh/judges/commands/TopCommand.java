package cs.agh.judges.commands;

import cs.agh.judges.AbstractJudgementPiece;
import cs.agh.judges.Judge;
import cs.agh.judges.Regulation;

import java.util.List;

public class TopCommand implements AbstractCommand {
    @Override
    public String getCommandName() {
        return "top";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.size() != 1)
            throw new RuntimeException("did't provide type to top command");

        Class<? extends AbstractJudgementPiece> myClass = Judge.class;

        switch (arguments.get(0)) {
            case "judges":
                break;
            case "regulations":
                myClass = Regulation.class;
                break;
            default:
                throw new RuntimeException("wrong argument");
        }


        List<? extends AbstractJudgementPiece> myList = terminalState.factory.getTopPieces(myClass, 10);
        StringBuilder ret = new StringBuilder();

        for (int i = 1; i <= myList.size(); ++i) {
            AbstractJudgementPiece myJudge = myList.get(i - 1);
            ret.append(i).append(": ").append(myJudge.toString()).append(": ").append(myJudge.judgementList.size()).append("\n");
        }
        return ret.toString();
    }

    @Override
    public String help() {
        return "";
    }
}
