import java.util.UUID;

public class Account
{
    private volatile long money;
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

    public void block() {
        isBlocked = true;
    }

    public void unblock() {
        isBlocked = false;
    }

    public boolean withdraw(long amount) {
        if ((money < amount) || (isBlocked())) {
            System.out.println("Account number " + accNumber + " is blocked or doesn't have enough money (" + money + ")");
            return false;
        } else {
            setMoney(amount);
            return true;
        }
    }

    public boolean deposit(long amount) {
        if (isBlocked()) {
            System.out.println("Account number " + accNumber + " is blocked");
            return false;
        } else {
            setMoney(amount);
            return true;
        }
    }

    public void transferTo(Account accTo, long amount) {
        if (this.withdraw(amount)) {
            accTo.deposit(amount);
        }
    }
}
