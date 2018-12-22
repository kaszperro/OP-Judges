package cs.agh.judges.parsers;

import cs.agh.judges.JudgementDatabase;
import cs.agh.judges.judgementElements.CourtCase;
import cs.agh.judges.judgementElements.CourtType;
import cs.agh.judges.judgementElements.Judge;
import cs.agh.judges.judgementElements.JudgesSpecialRole;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//td.info-list-label
//td.info-list-value


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


            Map<Judge, JudgesSpecialRole[]> judgesMap = null;

            Elements elementsKeys = doc.select("td.info-list-label");
            Elements elementsValues = doc.select("td.info-list-value");
            for (int i = 0; i < elementsKeys.size(); ++i) {
                Element key = elementsKeys.get(i);
                Element val = elementsValues.get(i);

                if (key.text().contains("Sąd")) {
                    courtType = getCourtType(val);
                } else if (key.text().contains("orzeczenia")) { //Judgement date
                    judgmentDate = getJudgementDate(val);
                } else if (key.text().contains("Sędziowie")) { //Judges
                    judgesMap = getJudgesMap(val);
                }

                // System.out.println("key " + key.text() + ", val: " + val.html());
            }


            // judges.forEach(j -> System.out.println(j.toString()));

        } catch (IOException e) {
            e.printStackTrace();
        }
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

        for (JudgesSpecialRole role : judgesSpecialRoles) {
            System.out.println(role.toString());
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
