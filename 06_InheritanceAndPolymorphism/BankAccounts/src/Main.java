public class Main {

    public static void main(String[] args) {
        DepositaryAccount depositaryAccount = new DepositaryAccount(1000);
        depositaryAccount.withdraw(1500);
        depositaryAccount.deposit(50);
        depositaryAccount.withdraw(100);

        System.out.println("==============================");

        FeeCardAccount feeCardAccount = new FeeCardAccount(1500);
        feeCardAccount.deposit(25);
        feeCardAccount.withdraw(25);
        feeCardAccount.getBalance();

        System.out.println("==============================");

        feeCardAccount.send(depositaryAccount, 500);
        depositaryAccount.send(feeCardAccount, 100);
    }

}
