package cs.agh.judges.commands;

import cs.agh.judges.IJudgementElement;
import cs.agh.judges.Judge;
import cs.agh.judges.JudgementFactory;
import cs.agh.judges.Regulation;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TopCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "top";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.size() != 1)
            throw new RuntimeException("did't provide type to top command");

        JudgementFactory factory = terminalState.factory;

        List<? extends IJudgementElement> sortedList;

        switch (arguments.get(0)) {
            case "judges":
                sortedList = factory.getJudges();
                break;
            case "regulations":
                sortedList = factory.getRegulations();
                break;
            default:
                throw new RuntimeException("wrong argument");
        }
        sortedList.sort(Comparator.comparingInt((IJudgementElement o) -> o.getJudgementList().size()).reversed());
        
        StringBuilder ret = new StringBuilder();

        for (int i = 1; i <= Math.min(sortedList.size(), 10); ++i) {
            IJudgementElement myElement = sortedList.get(i - 1);
            ret.append(i).append(": ").append(myElement.toString()).append(": ").append(myElement.getJudgementList().size()).append("\n");
        }
        return ret.toString();
    }

    @Override
    public String help() {
        return "";
    }
}
