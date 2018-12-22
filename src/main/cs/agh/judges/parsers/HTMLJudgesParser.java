package cs.agh.judges.parsers;

import cs.agh.judges.JudgementDatabase;
import cs.agh.judges.judgementElements.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class HTMLJudgesParser implements IJudgesParser {
    @Override
    public String getSupportedExtension() {
        return ".html";
    }

    @Override
    public void parseFile(String filePath, JudgementDatabase judgementDatabase) {
        try {
            File input = new File(filePath);
            Document doc = Jsoup.parse(input, "UTF-8");

            CourtType courtType = null;
            CourtCase courtCase = getCourtCase(doc);

            LocalDate judgmentDate = null;
            String textContent = getTextContent(doc);

            Map<Judge, JudgesSpecialRole[]> judgesMap = new HashMap<>();

            List<AbstractRegulation> referencedRegulations = new LinkedList<>();

            Elements elementsKeys = doc.select("td.info-list-label");
            Elements elementsValues = doc.select("td.info-list-value");
            for (int i = 0; i < elementsKeys.size(); ++i) {
                Element key = elementsKeys.get(i);
                Element val = elementsValues.get(i);

                if (key.text().contains("Sąd")) {                       //Court type
                    courtType = getCourtType(val);
                } else if (key.text().contains("orzeczenia")) {         //Judgement date
                    judgmentDate = getJudgementDate(val);
                } else if (key.text().contains("Sędziowie")) {          //Judges
                    judgesMap = getJudgesMap(val);
                } else if (key.text().contains("przepisy")) {           //Referenced regulations
                    referencedRegulations = getReferencedRegulations(val);
                }
            }

            List<CourtCase> courtCaseSingleton = Collections.singletonList(courtCase);

            Judgement myJudgment = new Judgement(
                    courtType,
                    courtCaseSingleton,
                    judgesMap,
                    referencedRegulations,
                    textContent,
                    judgmentDate
            );

            judgementDatabase.addJudgement(myJudgment);
            judgementDatabase.addJudgementToJudges(judgesMap.keySet().toArray(new Judge[0]), myJudgment);
            judgementDatabase.addJudgementToCourtCases(courtCaseSingleton.toArray(new CourtCase[0]), myJudgment);
            judgementDatabase.addJudgementToCourtType(courtType, myJudgment);
            judgementDatabase.addJudgementToRegulations(referencedRegulations.toArray(new AbstractRegulation[0]), myJudgment);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<AbstractRegulation> getReferencedRegulations(Element regulationsElement) {
        List<AbstractRegulation> referencedRegulations = new LinkedList<>();
        Elements regulationsElements = regulationsElement.getElementsByClass("nakt");
        for (Element elem : regulationsElements) {
            referencedRegulations.add(new HTMLRegulation(elem.text()));
        }
        return referencedRegulations;
    }

    private String getTextContent(Document doc) {
        return doc.select("span.info-list-value-uzasadnienie").text();
    }

    private CourtType getCourtType(Element courtTypeElement) {
        if (courtTypeElement.html().contains("czelny"))
            return CourtType.MAIN_ADMINISTRATIVE;
        return CourtType.REGIONAL_ADMINISTRATIVE;
    }

    private JudgesSpecialRole getRoleFromString(String role) {
        switch (role) {
            case "przewodniczący":
                return JudgesSpecialRole.PRESIDING_JUDGE;
            case "sprawozdawca":
                return JudgesSpecialRole.REPORTING_JUDGE;
            default:
                return null;
        }
    }

    private void getJudgeToMap(String htmlCode, Map<Judge, JudgesSpecialRole[]> judgeHashMap) {
        int firstSlashIndex = htmlCode.indexOf('/');
        int lastSlashIndex = htmlCode.lastIndexOf('/');

        String judgeName = htmlCode.replaceAll("( |)/.*", "");


        String[] judgeRolesString = new String[]{};
        if (firstSlashIndex != -1 && lastSlashIndex != -1) {
            String onlyRoles = htmlCode.substring(firstSlashIndex + 1, lastSlashIndex);
            judgeRolesString = onlyRoles.split("\\s+");
        }

        List<JudgesSpecialRole> judgesSpecialRoles = new LinkedList<>();

        for (String strRole : judgeRolesString) {
            JudgesSpecialRole myRole = getRoleFromString(strRole);
            if (myRole != null) {
                judgesSpecialRoles.add(myRole);
            }
        }


        judgeHashMap.put(new Judge(judgeName), judgesSpecialRoles.toArray(new JudgesSpecialRole[0]));

    }

    private Map<Judge, JudgesSpecialRole[]> getJudgesMap(Element judgesElement) {
        String allJudgesHTML = judgesElement.html();

        String[] judgesHTML = allJudgesHTML.split("<br>|\n");

        Map<Judge, JudgesSpecialRole[]> judgesMap = new HashMap<>();

        for (String judgeHTML : judgesHTML) {
            getJudgeToMap(judgeHTML, judgesMap);
        }

        return judgesMap;
    }


    private CourtCase getCourtCase(Document document) {
        String courtCaseString = document.getElementById("warunek").text();
        return new CourtCase(courtCaseString.substring(0, courtCaseString.lastIndexOf(" -")));
    }

    private LocalDate getJudgementDate(Element element) {
        String dateString = Jsoup.parse(element.html())
                .getElementsByTag("td").get(0).text();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
}
