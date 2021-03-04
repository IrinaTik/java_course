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

    // можно ли снимать деньги со счета
    private boolean isReadyToWithdraw(long amount) {
        if ((this.money < amount) || (this.isBlocked)) {
            System.out.println("Account number " + accNumber + (this.isBlocked ? " is blocked" : " doesn't have enough money (" + money + ")"));
            return false;
        } else {
            return true;
        }
    }

    // единичное снятие денег
    public void withdraw(long amount) {
        if (isReadyToWithdraw(amount)) {
            this.setMoney(this.money - amount);
        }
    }

    // можно ли класть деньги на счет
    private boolean isReadyToDeposit() {
        if (this.isBlocked) {
            System.out.println("Account number " + accNumber + " is blocked");
            return false;
        } else {
            return true;
        }
    }

    // единичный вклад денег
    public void deposit(long amount) {
        if (this.isReadyToDeposit()) {
            this.setMoney(this.money + amount);
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
        if (this.isReadyToWithdraw(amount) && accTo.isReadyToDeposit()) {
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
            return new Random().nextInt(MONEY_THRESHOLD) + MONEY_THRESHOLD;
        }
    }
}
