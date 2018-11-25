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
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.ListResourceBundle;

public class JudgesSystem {
    public static void main(String[] args) throws IOException {
        /*
        List<String> filePaths;
        try {
            filePaths = JsonParser.getFilePaths("Resources/json");
            for (String filePath : filePaths) {
               System.out.println(filePath);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }*/


        JudgementFactory factory = new JudgementFactory();


        try {
            JsonParser.parseFile("Resources/judgments.json", factory);
        } catch (ParseException e) {
            System.out.println("Parse problem");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO problem");
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
/*
        List<Judge> judgeList = factory.getPiecesOfType(Judge.class);

        for (Judge judge : judgeList) {
            System.out.println(judge.name + " " + judge.judgementList.size());
            System.out.println(judge.judgementList.get(0).id);
        }

        List<Court> courtTypeList = factory.getPiecesOfType(Court.class);
        for (Court courtType : courtTypeList) {
            System.out.println(courtType.courtType.toString() + " " + courtType.judgementList.size());
        }
*/

        CourtCase myCase = factory.findPiece(new CourtCase("U 3/86"));
        if (myCase == null) {
            System.out.println("NIe ma takiej");
        } else {
            System.out.println(myCase.judgementList.get(0).id);
        }


    }
}