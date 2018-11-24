package cs.agh.judges;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public class JudgesSystem {
    public static void main(String[] args) {

        JudgementFactory factory = new JudgementFactory();

        JsonParser parser = new JsonParser();
        try {
            parser.parseFile("Resources/judgments.json", factory);
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
            System.out.println(judge.name);
            System.out.println(judge.judgementList.size());
        }

    }
}
