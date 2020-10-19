public class FeeCardAccount extends BankAccount{

    private static final double FEE = 0.01;

    public FeeCardAccount(double moneyAmount) {
        super(moneyAmount);
        System.out.println("Fee card account (" + moneyAmount + " gold) is created");
    }

    @Override
    public void withdraw(double amount) {
        double amountWithFee = amount + amount * FEE;
        super.withdraw(amountWithFee);
    }
}
