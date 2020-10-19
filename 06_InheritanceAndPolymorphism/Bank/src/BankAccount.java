public class BankAccount {

    private double moneyAmount;

    public BankAccount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    private boolean isValid(double amount) {
        if (this.moneyAmount < amount) {
            System.out.println("Not sufficient funds to withdraw " + amount + " gold");
            return false;
        } else {
            return true;
        }
    }

    public void withdraw(double amount) {
        if (isValid(amount)) {
            this.moneyAmount -= amount;
            System.out.println(amount + " withdrawn");
        }
    }

    public void deposit(double amount) {
        this.moneyAmount += amount;
        System.out.println(amount + " deposited");
    }

    public void getMoneyAmount() {
        System.out.println("You have " + moneyAmount + " gold remaining");
    }

    public boolean send(BankAccount receiver, double amount) {
        if (isValid(amount)) {
            this.withdraw(amount);
            System.out.println("Sender now has " + this.moneyAmount + " gold");
            receiver.deposit(amount);
            System.out.println("Receiver now has " + receiver.moneyAmount + " gold");
            return true;
        } else {
            return false;
        }
    }
}
