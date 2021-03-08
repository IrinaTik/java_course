import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class BankSimulator {

    private static final int accountsCount = 10;

    public static void main(String[] args) {

        Bank bank = new Bank(accountsCount);
        long balanceBefore = bank.getBankBalance();

        try {
            startTransfers(bank);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Bank money before transactions: " + balanceBefore);
        System.out.println("Bank money after transactions: " + bank.getBankBalance());
    }

    private static void startTransfers(Bank bank) throws InterruptedException {
        List<Account> accounts = new ArrayList<>(bank.getAccounts().values());
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int index1 = getRandomIndex();
            int index2 = getRandomIndex(index1);
            String uuid1 = accounts.get(index1).getAccNumber();
            String uuid2 = accounts.get(index2).getAccNumber();
            Runnable task = () -> bank.transfer(uuid1, uuid2, Account.getTransferAmount());
            futures.add(executor.submit(task));
        }
        // вместо executor.shutdown() и executor.awaitTermination(100, TimeUnit.SECONDS)
        futures.forEach(res -> {
            try {
                res.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    private static int getRandomIndex() {
        return new Random().nextInt(accountsCount);
    }

    private static int getRandomIndex(int anotherIndex) {
        while (true) {
            int index = new Random().nextInt(accountsCount);
            if (index != anotherIndex) {
                return index;
            }
        }
    }

}
