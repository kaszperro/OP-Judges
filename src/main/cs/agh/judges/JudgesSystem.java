package cs.agh.judges;

import cs.agh.judges.commands.*;
import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JudgesSystem {

    private static final String REGEX_PATTERN = "\"([^\"]*)\"|(\\S+)";

    public static void main(String[] args) throws IOException {

        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build();

        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();


        JudgementDatabase judgementDatabase = new JudgementDatabase();

        TerminalState terminalState = new TerminalState(judgementDatabase);
        setTerminalState(terminalState);

        while (true) {
            String myLine = lineReader.readLine("insert command> ");

            Matcher m = Pattern.compile(REGEX_PATTERN).matcher(myLine);

            List<String> splitLine = splitFromMatcher(m);

            for (String s : splitLine) {
                terminal.writer().println(s);
            }


            if (splitLine.size() == 0) {
                terminal.writer().println("Empty command");
                continue;
            }
            String myCommand = splitLine.get(0);

            List<String> myArguments = new LinkedList<>();
            if (splitLine.size() > 1) {
                myArguments = splitLine.subList(1, splitLine.size());
            }

            boolean helpMode = myArguments.size() > 0 && myArguments.get(0).equals("-h");
            boolean commandFound = false;

            for (ICommand command : terminalState.possibleCommands) {
                if (command.getCommandName().equals(myCommand)) {
                    if (helpMode) {
                        terminal.writer().println(
                                AttributedString.fromAnsi("\u001b[36m" + command.help())
                                        .toAnsi(terminal));
                    } else {


                        try {
                            String result = command.run(terminalState, myArguments);
                            terminal.writer().println(result);
                        } catch (Exception e) {
                            terminal.writer().println(
                                    AttributedString.fromAnsi("\u001b[31m" + e.getMessage())
                                            .toAnsi(terminal));

                        }
                    }
                    commandFound = true;
                    break;

                }
            }

            if (!commandFound) {
                terminal.writer().println(
                        AttributedString.fromAnsi("\u001b[33m" + "Command not found")
                                .toAnsi(terminal));
            }

        }

    }

    private static List<String> splitFromMatcher(Matcher m) {
        List<String> splitLine = new LinkedList<>();
        while (m.find()) {
            splitLine.add(
                    Optional.ofNullable(m.group(1))
                            .orElse(m.group(2)));
        }
        return splitLine;
    }

    private static void setTerminalState(TerminalState terminalState) {
        terminalState.createCommand(new ListCommand());
        terminalState.createCommand(new PwdCommand());
        terminalState.createCommand(new LoadJudgementCommand());
        terminalState.createCommand(new TopCommand());
        terminalState.createCommand(new MetricCommand());
        terminalState.createCommand(new StatsCommand());
        terminalState.createCommand(new ExplanationCommand());
        terminalState.createCommand(new ExitCommand());
        terminalState.createCommand(new HelpCommand());
    }
}