package cs.agh.judges.commands;

import cs.agh.judges.JudgementDatabase;
import cs.agh.judges.parsers.ParserContainer;


import java.util.LinkedList;
import java.util.List;

public class TerminalState {

    public JudgementDatabase judgementDatabase;
    public final List<ICommand> possibleCommands = new LinkedList<>();

    private ParserContainer parserContainer = new ParserContainer();


    public TerminalState(JudgementDatabase judgementDatabase) {

        this.judgementDatabase = judgementDatabase;

    }

    public void createCommand(ICommand command) {

        possibleCommands.add(command);
    }

    public void setParserContainer(ParserContainer parserContainer) {
        this.parserContainer = parserContainer;
    }


    ParserContainer getParserContainer() {
        return parserContainer;
    }

    public   ICommand getCommandFromName(String commandName) {
        for (ICommand command : possibleCommands) {
            if (command.getCommandName().equals(commandName))
                return command;

        }
        return null;
    }

}
