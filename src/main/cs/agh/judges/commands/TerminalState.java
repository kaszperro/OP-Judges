package cs.agh.judges.commands;

import cs.agh.judges.JudgementDatabase;

import java.util.LinkedList;
import java.util.List;

public class TerminalState {
    public String currentPath;
    public JudgementDatabase judgementDatabase;

    public final List<ICommand> possibleCommands = new LinkedList<>();

    TerminalState(String currentPath, JudgementDatabase judgementDatabase) {

        this.currentPath = currentPath;
        this.judgementDatabase = judgementDatabase;
    }

    public TerminalState(JudgementDatabase judgementDatabase) {

        this.judgementDatabase = judgementDatabase;
        currentPath = System.getProperty("user.dir");
    }

    public ICommand createCommand(ICommand command) {

        possibleCommands.add(command);
        return command;
    }

}
