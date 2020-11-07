import org.apache.commons.validator.routines.EmailValidator;

public class Customer
{
    private String name;
    private String phone;
    private String eMail;

    private static final String PHONE_EXAMPLE = "+79215637722";
    private static final String EMAIL_EXAMPLE = "vasily.petrov@gmail.com";

    public Customer(String name, String phone, String eMail) throws IllegalArgumentException {
        this.name = name;
        setMail(eMail);
        setPhone(phone);
    }

    public void setPhone(String phone) throws IllegalArgumentException {
        if (!isPhoneCorrect(phone)) {
            throw new IllegalArgumentException("Wrong phone format! Example: " + PHONE_EXAMPLE);
        } else {
            this.phone = phone;
        }
    }

    private boolean isPhoneCorrect(String phone) {
        return (phone.matches("^[+7]\\d+")) && (phone.length() == 12);
    }

    public void setMail(String eMail) throws IllegalArgumentException {
        if (!isEmailCorrect(eMail)) {
            throw new IllegalArgumentException("Wrong email format! Example: " + EMAIL_EXAMPLE);
        } else {
            this.eMail = eMail;
        }
    }

    private boolean isEmailCorrect(String eMail) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(eMail);
    }

    public String toString()
    {
        return name + " - " + eMail + " - " + phone;
    }
}
