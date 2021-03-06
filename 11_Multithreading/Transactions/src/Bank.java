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
        this.accounts = new HashMap<>();
        // генерация аккаунтов
        for (int i = 0; i < accountsCount; i++) {
            Account account = new Account(new Random().nextInt(150_000));
            this.accounts.put(account.getAccNumber(), account);
        }
    }

    public boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException
    {
        Account acc1 = this.accounts.get(fromAccountNum);
        Account acc2 = this.accounts.get(toAccountNum);
        synchronized ((fromAccountNum.compareTo(toAccountNum) > 0) ? acc1 : acc2) {
            synchronized ((fromAccountNum.compareTo(toAccountNum) > 0) ? acc2 : acc1) {
                Thread.sleep(1000);
            }
        }
        return random.nextBoolean();
    }

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
            System.out.println("Fraud! Transfer denied!");
            blockAccounts(fromAccountNum, toAccountNum);
        } else {
            doTransferAction(fromAccountNum, toAccountNum, amount);
        }
    }

    private void doTransferAction(String fromAccountNum, String toAccountNum, long amount) {
        accounts.get(fromAccountNum).transferTo(accounts.get(toAccountNum), amount);
    }


    // блокировка аккаунтов
    private void blockAccounts(String fromAccountNum, String toAccountNum) {
        accounts.get(fromAccountNum).block();
        accounts.get(toAccountNum).block();
    }

    // баланс одного счета
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

    // баланк всего банка
    public long getBankBalance() {
        long bankBalance = 0;
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            bankBalance = bankBalance + entry.getValue().getMoney();
        }
        return bankBalance;
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }
}
