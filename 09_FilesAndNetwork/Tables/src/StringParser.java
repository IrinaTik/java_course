import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser {

    public static String getOrgFromDescription(String description) {
        String res = "";
        Pattern pattern = Pattern.compile("(\\\\|/)([a-zA-Z].+)((\\d{2}\\.\\d{2}\\.\\d{2}\\s+){2})");
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            res = matcher.group(2).trim();
        }
        return res;
    }

    public static double getTotal(List<List<String>> info, int column) {
        double sum = 0;
        for (int i = 0; i < info.size(); i++) {
            sum += convertIntoDouble(info.get(i).get(column), i);
        }
        return sum;
    }

    public static double convertIntoDouble(String s, int lineNum) {
        try {
            return Double.parseDouble(s);
        } catch (RuntimeException ex) {
            throw new ParseDoubleException("RuntimeException in " + (lineNum + 1) + " element");
        }
    }

    public static List<String> getAllLineFragments(String line, int columnCount, int i) throws Exception {
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
