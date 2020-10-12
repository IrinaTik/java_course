import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailList {

    private TreeSet<String> set;
    private final String reg = "[\\d\\w]+@[\\d\\w]+\\.[\\w]+";

    public EmailList() {
        set = new TreeSet<>();
    }

    public void add(String email) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            set.add(email);
        } else {
            System.out.println("Неверный формат адреса почты");
        }
    }

    public void printList() {
        for (String s : set) {
            System.out.println(s);
        }
    }
}
