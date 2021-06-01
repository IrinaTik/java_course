import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Date;

public class DBRedisStorage {

    // имя sorted set'а
    private final static String SET_NAME = "USERS";
    // количество пользователей
    public final static int USERS_COUNT = 20;
    // разница во времени регистрации пользователей
    private final static int TIME_DIFF_MILLIES = 1;

    private RedissonClient redis;
    private RKeys keys;
    private RScoredSortedSet<String> users;

    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redis = Redisson.create();
        keys = redis.getKeys();
        keys.delete(SET_NAME);
    }

    public void outputRedisUsers() {
        for (String user : users) {
            System.out.println("Hello to " + user);
        }
    }

    public RScoredSortedSet<String> getRedisUsers() {
        return users;
    }

    // изначальное заполнение sorted set'а
    public void fillRedisUsers() {
        users = redis.getScoredSortedSet(SET_NAME);
        for (int i = 1; i <= USERS_COUNT; i++) {
            addUser(getCurrentTime(), "User" + i);
            try {
                Thread.sleep(TIME_DIFF_MILLIES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // добавить нового \ изменить существующего пользователя
    private void addUser(double time, String user) {
        users.add(time, user);
    }

    // платное перемещение юзера в начало
    public void paidChangeUsersOrder(String user) {
        double newFirstScore = users.firstScore() - TIME_DIFF_MILLIES;
        addUser(newFirstScore, user);
    }

    // штатное перемещение юзера в конец
    public void defaultChangeUsersOrder(String user) {
        double newFirstScore = users.lastScore() + TIME_DIFF_MILLIES;
        addUser(newFirstScore, user);
    }

    private double getCurrentTime() {
        return new Date().getTime() / 1000;
    }

    public void stop() {
        redis.shutdown();
    }
}
