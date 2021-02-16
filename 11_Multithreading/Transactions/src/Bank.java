import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

public class Bank
{
    private HashMap<String, Account> accounts;
    private final Random random = new Random();

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException
    {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount)
    {
        // проверка на существование акков
        if ((!accounts.containsKey(fromAccountNum)) || (!accounts.containsKey(toAccountNum))) {
            System.out.println("Cannot transfer");
        } else {
            Account fromAccount;
            Account toAccount;
            if (amount > 50_000) {
                try {
                    checkForFraud(fromAccountNum, toAccountNum, amount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // перевод денег
            }
        }

    }

    private void checkForFraud(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        if (isFraud(fromAccountNum, toAccountNum, amount)) {
            blockAccounts(fromAccountNum, toAccountNum);
        } else {
            doTransferAction(fromAccountNum, toAccountNum, amount);
        }
    }

    private void doTransferAction(String fromAccountNum, String toAccountNum, long amount) {
        // перевод денег
    }

    private void blockAccounts(String fromAccountNum, String toAccountNum) {
        // блок акков
    }

    private Account accountExist(String accountNum) {
        if (accounts.containsKey(accountNum)) {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            if (entry.getKey().equals(accountNum)) {
                entry.
                return entry.getValue();
            }
        }
        return null;
    }

    public long getBalance(String accountNum)
    {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account curAccount = entry.getValue();
            if (curAccount.getAccNumber().equals(accountNum)) {
                return curAccount.getMoney();
            }
        }
        // "невозможный" результат на случай, если ничего не найдется
        return -1;
    }
}
