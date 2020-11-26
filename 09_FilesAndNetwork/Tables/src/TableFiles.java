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
            System.out.println("Comings: " + StringParser.getTotal(moneyInfo, COMINGS_COLUMN));
            System.out.println("Debit: " + StringParser.getTotal(moneyInfo, DEBIT_COLUMN));
            formOrgDebit(moneyInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    private static void formOrgDebit(List<List<String>> info) {
        Map<String, Double> orgsDebit = new HashMap<>();

        for (int i = 0; i < info.size(); i++) {
            String org = StringParser.getOrgFromDescription(info.get(i).get(DESCRIPTION_COLUMN));
            Double debit = StringParser.convertIntoDouble(info.get(i).get(DEBIT_COLUMN), i);
            orgsDebit.put(org, orgsDebit.containsKey(org) ? orgsDebit.get(org) + debit : debit);
        }

        System.out.println("Debit by organization: ");
        for (Map.Entry<String, Double> entry : orgsDebit.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
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
            info.add(StringParser.getAllLineFragments(line, columnCount, i));
        }
        return info;

    }

}
