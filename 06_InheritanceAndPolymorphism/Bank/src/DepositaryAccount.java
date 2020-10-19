import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DepositaryAccount extends BankAccount {

    private Calendar lastActionDate;
    private static final long DAY_IN_MS = 24 * 60 * 60 * 1000;
    private static final long MONTH_IN_MS = 30 * DAY_IN_MS;

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
        Calendar currentDate = Calendar.getInstance();
        long duration = currentDate.getTimeInMillis() - lastActionDate.getTimeInMillis();
        return (duration - MONTH_IN_MS > 0);
    }

    private Calendar nextDate() {
        Calendar nextActionDate = Calendar.getInstance();
        int nextMonth = lastActionDate.get(Calendar.MONTH) + 1;
        nextActionDate.set(lastActionDate.get(Calendar.YEAR), nextMonth, lastActionDate.get(Calendar.DATE));
        return nextActionDate;

    }

    @Override
    public void withdraw(double amount) {
        if (isActionPossible()) {
            super.withdraw(amount);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            System.out.println("You tried to withdraw TOO SOON! Next withdrawal will be avaliable at " + format.format(nextDate().getTime()));
        }
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        changeLastActionDate();
    }
}
