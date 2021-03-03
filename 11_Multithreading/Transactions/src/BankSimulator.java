import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BankSimulator {

    private static final int accountsCount = 10;

    public static void main(String[] args) {

        Bank bank = new Bank(accountsCount);
        System.out.println("Bank money before transactions: " + bank.getBankBalance());

        startTransfers(bank);

        System.out.println("Bank money after transactions: " + bank.getBankBalance());
    }

    private static void startTransfers(Bank bank) {
        List<Account> accounts = new ArrayList<>(bank.getAccounts().values());
        for (int i = 0; i < 10; i++) {
            String uuid1 = accounts.get(getRandomIndex()).getAccNumber();
            String uuid2 = accounts.get(getRandomIndex()).getAccNumber();
            bank.transfer(uuid1, uuid2, Account.getTransferAmount());
        }
    }

    private static int getRandomIndex() {
        return new Random().nextInt(accountsCount);
    }

}
