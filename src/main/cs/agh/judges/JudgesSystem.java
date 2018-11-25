package cs.agh.judges;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.ListResourceBundle;

public class JudgesSystem {
    public static void main(String[] args) {
        List<String> filePaths;
        try {
            filePaths = JsonParser.getFilePaths("Resources/json");
            for (String filePath : filePaths) {
                System.out.println(filePath);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


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

        List<Judge> judgeList = factory.getPiecesOfType(Judge.class);

        for (Judge judge : judgeList) {
            System.out.println(judge.name + " " + judge.judgementList.size());
            System.out.println(judge.judgementList.get(0).id);
        }

        List<Court> courtTypeList = factory.getPiecesOfType(Court.class);
        for (Court courtType : courtTypeList) {
            System.out.println(courtType.courtType.toString() + " " + courtType.judgementList.size());
        }


    }
}
