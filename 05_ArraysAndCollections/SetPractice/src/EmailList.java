import org.apache.commons.validator.routines.EmailValidator;

import java.util.TreeSet;

public class EmailList {

    private TreeSet<String> set;

    public EmailList() {
        set = new TreeSet<>();
    }

    public void add(String email) {
        if (EmailValidator.getInstance().isValid(email)) {
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
