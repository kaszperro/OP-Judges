package cs.agh.judges;

import cs.agh.judges.commands.*;
import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JudgesSystem {
    public static void main(String[] args) throws IOException {

        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build();

        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();

        List<ICommand> possibleCommands = new LinkedList<>();
        possibleCommands.add(new ListCommand());
        possibleCommands.add(new PwdCommand());
        possibleCommands.add(new LoadJudgementCommand());
        possibleCommands.add(new TopCommand());
        possibleCommands.add(new RubrumCommand());

        JudgementFactory judgementFactory = new JudgementFactory();

        TerminalState terminalState = new TerminalState(judgementFactory);

        while (true) {
            String myLine = lineReader.readLine("insert command> ");

            String regex = "\"([^\"]*)\"|(\\S+)";
            Matcher m = Pattern.compile(regex).matcher(myLine);

            List<String> splitLine = new LinkedList<>();

            while (m.find()) {
                if (m.group(1) != null) {
                    splitLine.add(m.group(1));
                } else {
                    splitLine.add(m.group(2));

                }
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

            for (ICommand command : possibleCommands) {
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
}