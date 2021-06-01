import java.util.Iterator;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        DBRedisStorage storage = new DBRedisStorage();
        storage.init();

        storage.fillRedisUsers();

        int i = 0;
        Iterator<String> iterator = storage.getRedisUsers().iterator();
        while (iterator.hasNext()){
            i++;
            String user = iterator.next();
            System.out.println("Hello to " + user);
            storage.defaultChangeUsersOrder(user);
            Thread.sleep(500);
            if (i == 10) {
                i = 0;
                String userName = "User" + (new Random().nextInt(DBRedisStorage.USERS_COUNT - 1) + 1);
                storage.paidChangeUsersOrder(userName);
                System.out.println("=== " + userName + " paid ===");
                iterator = storage.getRedisUsers().iterator();
            }
        }

        storage.stop();
    }


}
