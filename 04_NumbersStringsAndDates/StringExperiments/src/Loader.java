public class Loader
{
    public static void main(String[] args)
    {
        String text = "Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей";

        System.out.println(text);

//        int firstComma = text.indexOf(',');
//        int lastComma = text.lastIndexOf(',');
//        String str1 = text.substring(0, firstComma).trim();
//        String str2 = text.substring(firstComma + 1, lastComma).trim();
//        String str3 = text.substring(lastComma + 1).trim();
//        System.out.println("Суммарный заработок: " + (getIntFromText(str1) + getIntFromText(str2) + getIntFromText(str3)));

        String[] strings = text.split(",\\s");

        int sum = 0;
        for (int i = 0; i < strings.length; i++) {
            sum += getIntFromText(strings[i]);
        }

        System.out.println("Суммарный заработок: " + sum);
    }

    private static Integer getIntFromText(String str) {
        int i = 0;
        String sInt = "";
        Character letter;
        while (i < str.length()) {
            letter = str.charAt(i);
            if (Character.isDigit(letter)) {
                sInt = sInt.concat(letter.toString());
            }
            i++;
        }
        return Integer.parseInt(sInt);
    }
}