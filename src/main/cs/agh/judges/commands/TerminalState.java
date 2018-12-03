package cs.agh.judges.commands;

import cs.agh.judges.JudgementFactory;
import org.jline.terminal.Terminal;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class TerminalState {
    public String currentPath;
    public JudgementFactory factory;

    public final List<ICommand> possibleCommands = new LinkedList<>();

    TerminalState(String currentPath, JudgementFactory factory) {

        this.currentPath = currentPath;
        this.factory = factory;
    }

    public TerminalState(JudgementFactory factory) {

        this.factory = factory;
        currentPath = System.getProperty("user.dir");
    }

    public ICommand createCommand(ICommand command) {

        possibleCommands.add(command);
        return command;
    }

}
