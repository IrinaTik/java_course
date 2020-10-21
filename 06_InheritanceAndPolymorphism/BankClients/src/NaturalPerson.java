public class NaturalPerson extends Client {

    public NaturalPerson(double money) {
        super(money);
    }

    @Override
    public void info() {
        System.out.println("Withdraw and deposit without fee");
        this.getBalance();
    }

}
