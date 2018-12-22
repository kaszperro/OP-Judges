package cs.agh.judges;

import cs.agh.judges.commands.*;
import cs.agh.judges.parsers.HTMLJudgesParser;
import cs.agh.judges.parsers.JSONJudgesParser;
import cs.agh.judges.parsers.ParserContainer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JudgesSystem {

    private static final String REGEX_PATTERN = "\"([^\"]*)\"|(\\S+)";

    //usage: provide data path as first argument
    //optional: provide commands result path as second argument
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new Exception("Didn't provide load path");
        }
        String savePath = null;
        if (args.length == 2) {
            savePath = args[1];
        }

        StringBuilder commandsHistoryString = new StringBuilder();

        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build();

        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();


        JudgementDatabase judgementDatabase = new JudgementDatabase();


        ParserContainer parserContainer = new ParserContainer();
        parserContainer.addParser(new HTMLJudgesParser());
        parserContainer.addParser(new JSONJudgesParser());


        TerminalState terminalState = new TerminalState(judgementDatabase);
        terminalState.setParserContainer(parserContainer);
        setTerminalCommands(terminalState);

        ICommand loadCommand = terminalState.getCommandFromName("load");
        loadCommand.run(terminalState, Collections.singletonList(args[0]));

        while (true) {
            String myLine = lineReader.readLine("insert command> ");

            commandsHistoryString.append("command: ").append(myLine).append("\n");

            Matcher m = Pattern.compile(REGEX_PATTERN).matcher(myLine);

            List<String> splitLine = splitFromMatcher(m);

            if (splitLine.size() == 0) {
                terminal.writer().println("Empty command");
                continue;
            }
            String commandName = splitLine.get(0);

            List<String> myArguments = new LinkedList<>();
            if (splitLine.size() > 1) {
                myArguments = splitLine.subList(1, splitLine.size());
            }

            ICommand myCommand = terminalState.getCommandFromName(commandName);

            if (myCommand == null) {
                terminal.writer().println(
                        AttributedString.fromAnsi("\u001b[33m" + "Command not found")
                                .toAnsi(terminal));
                commandsHistoryString.append("Command not found").append("\n----------------------------\n");

            } else {
                try {
                    String result = myCommand.run(terminalState, myArguments);
                    terminal.writer().println(result);
                    commandsHistoryString.append(result).append("\n----------------------------\n");
                    ;

                } catch (Exception e) {
                    terminal.writer().println(
                            AttributedString.fromAnsi("\u001b[31m" + e.getMessage())
                                    .toAnsi(terminal));
                    commandsHistoryString.append(e.getMessage()).append("\n----------------------------\n");
                }
            }
            if (savePath != null) {
                saveHistoryToFile(savePath, commandsHistoryString.toString());
            }

        }

    }

    private static void saveHistoryToFile(String filePath, String whatToSave) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(filePath))) {
            pw.print(whatToSave);
        } catch (FileNotFoundException e) {
            System.out.println("Could't save history");

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


    private static void setTerminalCommands(TerminalState terminalState) {
        terminalState.createCommand(new LoadJudgementCommand());
        terminalState.createCommand(new RubrumCommand());
        terminalState.createCommand(new MonthsCommand());
        terminalState.createCommand(new CourtsCommand());
        terminalState.createCommand(new RegulationsCommand());
        terminalState.createCommand(new ContentCommand());
        terminalState.createCommand(new ExitCommand());
        terminalState.createCommand(new HelpCommand());
        terminalState.createCommand(new JudgeCommand());
        terminalState.createCommand(new JudgesCommand());
        terminalState.createCommand(new JuryCommand());
    }

}