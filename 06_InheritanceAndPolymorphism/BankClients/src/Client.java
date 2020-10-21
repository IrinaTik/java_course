public abstract class Client {

    private double money;

    public Client(double money) {
        this.setMoney(money);
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public void getBalance() {
        System.out.println("Available balance " + this.getMoney() + " gold");
    }

    private boolean isValid(double amount) {
        return (this.getMoney() - amount >= 0);
    }

    public void deposit(double amount) {
        this.setMoney(this.getMoney() + positiveAmount(amount));
        System.out.println(positiveAmount(amount) + " deposited");
    }

    // слабенькая защита от "дурака" на случай переданного отрицательного значения amount
    private double positiveAmount(double amount) {
        return Math.abs(amount);
    }

    public boolean withdraw(double amount) {
        if (isValid(positiveAmount(amount))) {
            this.setMoney(this.getMoney() - positiveAmount(amount));
            System.out.println(positiveAmount(amount) + " withdrawn");
            return true;
        } else {
            System.out.println("Not sufficient funds to withdraw " + positiveAmount(amount) + " gold");
            return false;
        }
    }

    public abstract void info();
}
