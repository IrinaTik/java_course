import java.util.Random;

public class BankSimulator {

    private static final int accountsCount = 100;

    public static void main(String[] args) {

        Bank bank = new Bank(accountsCount);
        System.out.println("Bank money before transactions: " + bank.getBankBalance());



    }

    private int getRandomIndex() {
        return new Random().nextInt(accountsCount);
    }

    private void startTransfers(Bank bank) {

        for (int i = 0; i < 10_000; i++) {
            int index1 = getRandomIndex();
            int index2 = getRandomIndex();

        }
    }

}
