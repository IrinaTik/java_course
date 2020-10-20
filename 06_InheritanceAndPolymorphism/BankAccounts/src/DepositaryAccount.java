import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DepositaryAccount extends BankAccount {

    private Calendar lastActionDate;

    public DepositaryAccount(double moneyAmount) {
        super(moneyAmount);
        this.lastActionDate = Calendar.getInstance();
        this.lastActionDate.set(1990, 1, 1);
        System.out.println("Depositary account (" + moneyAmount + " gold) is created");
    }

    private void changeLastActionDate() {
        this.lastActionDate = Calendar.getInstance();
    }

    private boolean isActionPossible() {
        Calendar monthAgo = Calendar.getInstance();
        monthAgo.add(Calendar.MONTH, -1);
        return monthAgo.compareTo(lastActionDate) >= 0;
    }

    private Calendar nextDate() {
        Calendar nextActionDate = Calendar.getInstance();
        nextActionDate.setTime(lastActionDate.getTime());
        nextActionDate.add(Calendar.MONTH, 1);
        return nextActionDate;

    }

    @Override
    public boolean withdraw(double amount) {
        if (isActionPossible()) {
            return super.withdraw(amount);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            System.out.println("You tried to withdraw TOO SOON! Next withdrawal will be avaliable at " + format.format(nextDate().getTime()));
            return false;
        }
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        changeLastActionDate();
    }
}
