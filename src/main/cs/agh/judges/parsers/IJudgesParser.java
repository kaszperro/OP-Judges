package cs.agh.judges.parsers;

import cs.agh.judges.JudgementDatabase;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public interface IJudgesParser {

    String getSupportedExtension();

    void parseFile(String filePath, JudgementDatabase judgementDatabase);

}
