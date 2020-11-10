import Exceptions.EmailFormatException;
import Exceptions.PhoneFormatException;
import org.apache.commons.validator.routines.EmailValidator;

public class Customer
{
    private String name;
    private String phone;
    private String eMail;

    private static final String PHONE_EXAMPLE = "+79215637722";
    private static final String EMAIL_EXAMPLE = "vasily.petrov@gmail.com";

    public Customer(String name, String phone, String eMail) throws PhoneFormatException, EmailFormatException {
        this.name = name;
        setMail(eMail);
        setPhone(phone);
    }

    public void setPhone(String phone) throws PhoneFormatException {
        if (!isPhoneCorrect(phone)) {
            throw new PhoneFormatException("Wrong phone format! Example: " + PHONE_EXAMPLE);
        } else {
            this.phone = phone;
        }
    }

    private boolean isPhoneCorrect(String phone) {
        return (phone.matches("^[+7]\\d+")) && (phone.length() == 12);
    }

    public void setMail(String eMail) throws EmailFormatException {
        if (!isEmailCorrect(eMail)) {
            throw new EmailFormatException("Wrong email format! Example: " + EMAIL_EXAMPLE);
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
