package cs.agh.judges.parsers;

import cs.agh.judges.JudgementDatabase;

//td.info-list-label
//td.info-list-value

public interface IParser {

    public String getSupportedExtention();

    public void parseFile(String filePath, JudgementDatabase judgementDatabase);


}
