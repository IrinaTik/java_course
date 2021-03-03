import java.util.Random;
import java.util.UUID;

public class Account
{
    private static final int MONEY_THRESHOLD = 50_000;

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
        if (!this.isBlocked) {
            System.out.println("Account " + this.getAccNumber() + " is blocked now!");
            this.isBlocked = true;
        }
    }

    public synchronized void unblock() {
        if (this.isBlocked) {
            System.out.println("Account " + this.getAccNumber() + " is unblocked now!");
            this.isBlocked = false;
        }
    }

    // единичное снятие денег
    public boolean withdraw(long amount) {
        if ((money < amount) || (isBlocked())) {
            System.out.println("Account number " + accNumber + " is blocked or doesn't have enough money (" + money + ")");
            return false;
        } else {
            this.setMoney(this.money - amount);
            return true;
        }
    }

    // единичный вклад денег
    public boolean deposit(long amount) {
        if (isBlocked()) {
            System.out.println("Account number " + accNumber + " is blocked");
            return false;
        } else {
            this.setMoney(this.money + amount);
            return true;
        }
    }


    // проверка, можно ли проводить перевод между счетами
    private boolean isReadyForTransfer(Account account, long amount) {
        if ((this.money >= amount) && (!this.isBlocked) && (!account.isBlocked())) {
            return true;
        } else {
            return false;
        }
    }


    // перевод между счетами
    public void transferTo(Account accTo, long amount) {
        if (accNumber.compareTo(accTo.getAccNumber()) > 0) {
            synchronized (this) {
                synchronized (accTo) {
                    this.doTransferAction(accTo, amount);
                }
            }
        } else {
            synchronized (accTo) {
                synchronized (this) {
                    this.doTransferAction(accTo, amount);
                }
            }
        }
    }

    private void doTransferAction(Account accTo, long amount) {
        if (isReadyForTransfer(accTo, amount)) {
            this.withdraw(amount);
            accTo.deposit(amount);
            System.out.println("Transfer between accounts " + this.accNumber + " and " + accTo.getAccNumber() + " was successful with amount " + amount);
        } else {
            System.out.println("Transfer between accounts " + this.accNumber + " and " + accTo.getAccNumber() + " failed with amount " + amount);
        }
    }

    // генерирует сумму для перевода между счетами, в соответствии с правилом:
    //          транзакции на суммы >50000 не более 5% от всех
    public static int getTransferAmount() {
        int threshold = new Random().nextInt(100);
        if (threshold > 5) {
            return new Random().nextInt(MONEY_THRESHOLD) + 1;
        } else {
            return new Random().nextInt() + MONEY_THRESHOLD;
        }
    }
}
