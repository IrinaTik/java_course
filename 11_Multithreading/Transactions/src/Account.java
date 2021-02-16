import java.util.UUID;

public class Account
{
    private long money;
    private String accNumber;
    private boolean isBlocked;

    public Account(long money) {
        this.money = money;
        this.isBlocked = false;
        this.accNumber = UUID.randomUUID().toString();
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
