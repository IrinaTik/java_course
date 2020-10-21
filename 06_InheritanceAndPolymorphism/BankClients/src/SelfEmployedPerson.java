public class SelfEmployedPerson extends Client {

    private static final double FEE_LESS_1000 = 0.01;
    private static final double FEE_MORE_1000 = 0.005;

    public SelfEmployedPerson(double money) {
        super(money);
    }

    @Override
    public void deposit(double amount) {
        double amountWithFee;
        if (amount < 1000) {
            amountWithFee = amount + amount * FEE_LESS_1000;
            System.out.println("Fee 1%");
        } else {
            amountWithFee = amount + amount * FEE_MORE_1000;
            System.out.println("Fee 0.5%");
        }
        super.deposit(amountWithFee);
    }

    @Override
    public void info() {
        System.out.println("Deposit with 1% fee if money amount is lesser then 1000 and 0.5% fee otherwise");
        System.out.println("Withdraw without fee");
        this.getBalance();
    }

}
