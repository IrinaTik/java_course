import java.io.*;
import java.util.*;

public class TableFiles {

    private static final String SOURCE_PATH = "09_FilesAndNetwork/files/movementList.csv";
    private static final int DESCRIPTION_COLUMN = 5;
    private static final int COMINGS_COLUMN = 6;
    private static final int DEBIT_COLUMN = 7;

    public static void main(String[] args) {

        try {
            List<List<String>> moneyInfo = readFileIntoList(SOURCE_PATH);
            System.out.println("Comings: " + getTotal(moneyInfo, COMINGS_COLUMN));
            System.out.println("Debit: " + getTotal(moneyInfo, DEBIT_COLUMN));
            formOrgDebit(moneyInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    private static void formOrgDebit(List<List<String>> info) {
        Map<String, Double> orgsDebit = new HashMap<>();

        for (int i = 0; i < info.size(); i++) {
            String org = getOrgFromDescription(info.get(i).get(DESCRIPTION_COLUMN));
            Double debit = convert(info.get(i).get(DEBIT_COLUMN), i);
            orgsDebit.put(org, orgsDebit.containsKey(org) ? orgsDebit.get(org) + debit : debit);
        }

        System.out.println("Debit by organization: ");
        for (Map.Entry<String, Double> entry : orgsDebit.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    private static String addStrings(String s) {
        String res = "";
        String[] subS = s.split("(\\\\|/)");
        int number = -1;
        for (int i = 0; i < subS.length; i++) {
            if (subS[i].matches("[a-zA-Z]+.?")) {
                number = i;
                break;
            }
        }
        if (number != -1) {
            for (int i = number; i < subS.length; i++) {
                res += subS[i];
            }
        }
        return res.trim();
    }

    private static String getOrgFromDescription(String description) {
        String res = "";
        String[] arrDesc = description.split("\\s{4,}");
        for (String s : arrDesc) {
            s = s.trim();
            // ищем часть, где есть описание организации - она содержит слеши
            if (s.contains("\\") || s.contains("/")) {
                // бьем на части по слешам, ищем первую часть, которая начинается с букв
                // в результат пишем строку, начинающуюся с этой части, и до конца исходной строки
                // все слеши при этом "схлопываются"
                res = addStrings(s);
            }
            // после дат нас ничего не интересует
            if (s.matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
                break;
            }
        }
        return res.trim();
    }

    private static double getTotal(List<List<String>> info, int column) {
        double sum = 0;
        for (int i = 0; i < info.size(); i++) {
            sum += convert(info.get(i).get(column), i);
        }
        return sum;
    }

    private static double convert(String s, int lineNum) {
        try {
            return Double.parseDouble(s);
        } catch (RuntimeException ex) {
            throw new ParseDoubleException("RuntimeException in " + (lineNum + 1) + " element");
        }
    }

    private static List<List<String>> readFileIntoList(String path) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));

        String headline = reader.readLine();
        String[] headers = headline.split(",");
        int columnCount = headers.length;

        List<List<String>> info = new ArrayList<>();
        int i = 1;

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            i++;
            info.add(getAllLineFragments(line, columnCount, i));
        }
        return info;

    }

    private static List<String> getAllLineFragments(String line, int columnCount, int i) throws Exception {
        List<String> fragments;
        String[] bracketFragments = line.split("\"");
        if (bracketFragments.length != 1) {
            String commaNumber = bracketFragments[1];
            fragments = getSplittedInfo(bracketFragments[0], columnCount - 1, i);
            fragments.add(replaceComma(commaNumber));
        } else {
            fragments = getSplittedInfo(line, columnCount, i);
        }
        return fragments;
    }

    private static List<String> getSplittedInfo(String line, int length, int lineNumber) throws Exception {
        String[] commaFragments = line.split(",");
        if (commaFragments.length != length) {
            throw new WrongLineFormatException("Data format error in " + lineNumber + " line");
        } else {
            return new ArrayList<>(Arrays.asList(commaFragments));
        }
    }

    private static String replaceComma(String commaNumber) {
        return commaNumber.replace(',', '.');
    }

}
