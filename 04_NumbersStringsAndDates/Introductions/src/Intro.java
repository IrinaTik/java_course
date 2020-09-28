import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Intro {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String str = reader.readLine();

            String[] arrStr = str.split(" ");

            if (arrStr.length < 3) {
                if ((arrStr.length == 2) && (!arrStr[1].contains("вна")) && (!arrStr[1].contains("вич"))) {
                    // вариант для иностранцев, у которых нет отчества
                    output(arrStr[0], arrStr[1], "<пусто>");
                    break;
                } else {
                    System.out.println("Неверно введено ФИО. Попробуйте еще раз.");
                }
            } else {
                output(arrStr[0], arrStr[1], arrStr[2]);
                break;
            }
        }
    }

    private static void output(String str1, String str2, String str3) {
        System.out.println("Фамилия: " + str1);
        System.out.println("Имя: " + str2);
        System.out.println("Отчество: " + str3);
    }
}
