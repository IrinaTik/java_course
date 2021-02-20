import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Bank
{
    private HashMap<String, Account> accounts;
    private final Random random = new Random();

    public Bank() {
        HashMap<String, Account> accounts = new HashMap<>();
    }

    public Bank(int accountsCount) {
        HashMap<String, Account> accounts = new HashMap<>();
        for (int i = 0; i < accountsCount; i++) {
            Account account = new Account(new Random().nextInt(150_000));
            accounts.put(account.getAccNumber(), account);
        }
    }

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
            System.out.println("Cannot transfer, account number not found");
        } else {
            // банка-подозревака
            if (amount > 50_000) {
                try {
                    checkForFraud(fromAccountNum, toAccountNum, amount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // перевод денег
                doTransferAction(fromAccountNum, toAccountNum, amount);
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
        accounts.get(fromAccountNum).transferTo(accounts.get(toAccountNum), amount);
    }

    private void blockAccounts(String fromAccountNum, String toAccountNum) {
        accounts.get(fromAccountNum).block();
        accounts.get(toAccountNum).block();
    }

    public Long getBalance(String accountNum)
    {
        if (accounts.containsKey(accountNum)) {
            return accounts.get(accountNum).getMoney();
        } else {
            // "невозможный" результат на случай, если ничего не найдется
            System.out.println("Account number " + accountNum + " not found");
            return null;
        }
    }

    public long getBankBalance() {
        long bankBalance = 0;
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            bankBalance =+ entry.getValue().getMoney();
        }
        return bankBalance;
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }
}
