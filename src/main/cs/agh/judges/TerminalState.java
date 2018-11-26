package cs.agh.judges;

import org.jline.terminal.Terminal;

import java.nio.file.Path;

public class TerminalState {
    public String currentPath;

    public JudgementFactory factory;

    TerminalState(String currentPath, JudgementFactory factory) {

        this.currentPath = currentPath;
        this.factory = factory;
    }

    TerminalState(JudgementFactory factory) {

        this.factory = factory;
        currentPath = System.getProperty("user.dir");
    }
}
