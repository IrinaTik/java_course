public class BankAccount {

    private double moneyAmount;

    public BankAccount(double moneyAmount) {
        this.setMoneyAmount(moneyAmount);
    }

    private double getMoneyAmount() {
        return this.moneyAmount;
    }

    private void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    private boolean isValid(double amount) {
        if (this.getMoneyAmount() < amount) {
            System.out.println("Not sufficient funds to withdraw " + amount + " gold");
            return false;
        } else {
            return true;
        }
    }

    public boolean withdraw(double amount) {
        if (isValid(amount)) {
            this.setMoneyAmount(this.getMoneyAmount() - amount);
            System.out.println(amount + " withdrawn");
            return true;
        } else {
            System.out.println("Withdraw operation failed");
            return false;
        }
    }

    public void deposit(double amount) {
        this.setMoneyAmount(this.getMoneyAmount() + amount);
        System.out.println(amount + " deposited");
    }

    public void getBalance() {
        System.out.println("You have " + moneyAmount + " gold remaining");
    }

    public boolean send(BankAccount receiver, double amount) {
        if (isValid(amount) && this.withdraw(amount)) {
            System.out.println("Sender now has " + this.getMoneyAmount() + " gold");
            receiver.deposit(amount);
            System.out.println("Receiver now has " + receiver.getMoneyAmount() + " gold");
            return true;
        } else {
            System.out.println("Transfer of funds failed. Sender: " + this.getMoneyAmount() + ". Receiver: " + receiver.getMoneyAmount());
            return false;
        }
    }
}
