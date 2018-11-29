package cs.agh.judges.commands;

import cs.agh.judges.JudgementFactory;
import org.jline.terminal.Terminal;

import java.nio.file.Path;

public class TerminalState {
    public String currentPath;

    public JudgementFactory factory;

    TerminalState(String currentPath, JudgementFactory factory) {

        this.currentPath = currentPath;
        this.factory = factory;
    }

    public TerminalState(JudgementFactory factory) {

        this.factory = factory;
        currentPath = System.getProperty("user.dir");
    }
}
