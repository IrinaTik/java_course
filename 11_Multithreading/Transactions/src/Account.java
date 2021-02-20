import java.util.UUID;

public class Account
{
    private volatile long money;
    private final String accNumber;
    private volatile boolean isBlocked;

    public Account(long money) {
        this.money = money;
        this.isBlocked = false;
        this.accNumber = UUID.randomUUID().toString();
    }

    public Account(String accNumber, long money) {
        this.money = money;
        this.isBlocked = false;
        this.accNumber = accNumber;
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

    public boolean isBlocked() {
        return isBlocked;
    }

    public synchronized void block() {
        if (!isBlocked) {
            isBlocked = true;
        }
    }

    public synchronized void unblock() {
        if (isBlocked) {
            isBlocked = false;
        }
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

    private boolean isReadyForTransfer(Account account, long amount) {
        if ((this.money >= amount) && (!this.isBlocked) && (!account.isBlocked())) {
            return true;
        } else {
            return false;
        }
    }

    public void transferTo(Account accTo, long amount) {
        if (accNumber.compareTo(accTo.getAccNumber()) > 0) {
            synchronized (this) {
                synchronized (accTo) {
                    doTransferAction(accTo, amount);
                }
            }
        } else {
            synchronized (accTo) {
                synchronized (this) {
                    doTransferAction(accTo, amount);
                }
            }
        }
    }

    private void doTransferAction(Account accTo, long amount) {
        if (isReadyForTransfer(accTo, amount)) {
            this.withdraw(amount);
            accTo.deposit(amount);
            System.out.println("Transfer between accounts " + this.accNumber + " and " + accTo.getAccNumber() + " was successful");
        } else {
            System.out.println("Transfer between accounts " + this.accNumber + " and " + accTo.getAccNumber() + " failed");
        }
    }
}
