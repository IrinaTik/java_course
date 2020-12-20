import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

        String URL = "jdbc:mysql://localhost:3306/skillbox?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "1234";
        String avgQuery = "SELECT course_name, count(*) / (max(MONTH(subscription_date)) - min(MONTH(subscription_date)) + 1) AS average\n" +
                "FROM PurchaseList GROUP BY course_name ORDER BY course_name";

        try {
            Connection connection = DriverManager.getConnection(URL, user, pass);

            Statement statement = connection.createStatement();

            ResultSet madSQL = statement.executeQuery(avgQuery);

            while (madSQL.next()) {
                String courseName = madSQL.getString("course_name");
                String subDate = madSQL.getString("average");
                System.out.println(courseName + "\t - \t" + subDate);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
