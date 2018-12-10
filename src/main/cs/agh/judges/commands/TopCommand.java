package cs.agh.judges.commands;

import cs.agh.judges.JudgementDatabase;

import java.util.Comparator;
import java.util.List;

public class TopCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "top";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) {
        if (arguments.size() != 2)
            throw new RuntimeException("did't provide type to top command or number");

        JudgementDatabase judgementDatabase = terminalState.judgementDatabase;

        ju

        List<? extends IJudgementElement> sortedList;

        sortedList = getIJudgementElements(arguments, factory);
        sortedList.sort(Comparator.comparingInt((IJudgementElement o) -> o.getJudgementList().size()).reversed());

        StringBuilder ret = new StringBuilder();
        int howMany = Integer.parseInt(arguments.get(1));
        for (int i = 1; i <= Math.min(sortedList.size(), howMany); ++i) {
            IJudgementElement myElement = sortedList.get(i - 1);
            ret.append(i).append(": ").append(myElement.toString()).append(": ").append(myElement.getJudgementList().size()).append("\n");
        }
        return ret.toString();
    }

    private List<? extends IJudgementElement> getIJudgementElements(List<String> arguments, JudgementDatabase factory) {
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
        return sortedList;
    }

    @Override
    public String help() {
        return "sorted display, usage:\n\ttop judges n displays top n judges\n\ttop regulations n displays top n regulations";
    }
}
