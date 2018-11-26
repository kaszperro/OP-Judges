package cs.agh.judges;

import org.beryx.textio.*;
import org.beryx.textio.jline.JLineTextTerminal;
import org.jline.reader.*;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Attributes;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.Month;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListResourceBundle;

public class JudgesSystem {
    public static void main(String[] args) throws IOException {

        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build();

        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();


        List<AbstractCommand> possibleCommands = new LinkedList<>();
        possibleCommands.add(new ListCommand());
        possibleCommands.add(new PwdCommand());

        JudgementFactory judgementFactory = new JudgementFactory();

        TerminalState terminalState = new TerminalState(judgementFactory);

        while (true) {
            String myLine = lineReader.readLine("insert line> ");
            List<String> splitLine = Arrays.asList(myLine.split("\\s+"));
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

            for (AbstractCommand command : possibleCommands) {
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

            if(!commandFound) {
                terminal.writer().println(
                        AttributedString.fromAnsi("\u001b[33m" + "Command not found")
                                .toAnsi(terminal));
            }

        }

    }
}