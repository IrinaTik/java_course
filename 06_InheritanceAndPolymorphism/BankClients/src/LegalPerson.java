public class LegalPerson extends Client {

    private static final double FEE = 0.01;

    public LegalPerson(double money) {
        super(money);
    }

    @Override
    public boolean withdraw(double amount) {
        double amountWithFee = amount + amount * FEE;
        return super.withdraw(amountWithFee);
    }

    @Override
    public void info() {
        System.out.println("Deposit without fee");
        System.out.println("Withdraw with 1% fee");
        this.getBalance();
    }

}
