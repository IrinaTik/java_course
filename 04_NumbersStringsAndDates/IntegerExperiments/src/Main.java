public class Main
{
    public static void main(String[] args)
    {
        Container container = new Container();
        container.count += 7843;
        System.out.println("Сумма цифр числа 12345: " + sumDigits(12345));
        System.out.println("Сумма цифр числа 10: " + sumDigits(10));
        System.out.println("Сумма цифр числа 5059191: " + sumDigits(5059191));
        System.out.println();
        System.out.println("Сумма цифр числа 12345 (с использованием метода из Character): " + sumDigitsCharacterUse(12345));
        System.out.println("Сумма цифр числа 10 (с использованием метода из Character): " + sumDigitsCharacterUse(10));
        System.out.println("Сумма цифр числа 5059191 (с использованием метода из Character): " + sumDigitsCharacterUse(5059191));
    }

    public static Integer sumDigits(Integer number)
    {
        String sNumber = Integer.toString(number);
        int sum = 0;
        for (int i = 0; i < sNumber.length(); i++) {
            sum += Integer.parseInt(String.valueOf(sNumber.charAt(i)));
        }
        return sum;
    }


    public static Integer sumDigitsCharacterUse(Integer number)
    {
        String sNumber = Integer.toString(number);
        char[] arrChar = sNumber.toCharArray();
        int sum = 0;
        for (int i = 0; i < arrChar.length; i++) {
            sum += Character.getNumericValue(arrChar[i]);
        }
        return sum;
    }
}
