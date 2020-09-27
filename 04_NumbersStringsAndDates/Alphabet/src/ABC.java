public class ABC {

    public static void main(String[] args) {
        String engAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char letter;
        System.out.println("================================");
        System.out.println("English alphabet + Unicode codes");
        for (int i = 0; i < engAlphabet.length(); i++) {
            letter = engAlphabet.charAt(i);
            System.out.println(letter + " - \\u00" + Integer.toHexString( (int) letter));
        }

        System.out.println("================================");
        System.out.println("Russian alphabet + Unicode codes");
        String rusAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        for (int i = 0; i < rusAlphabet.length(); i++) {
            letter = rusAlphabet.charAt(i);
            System.out.println(letter + " - \\u0" + Integer.toHexString( (int) letter));
        }
    }

}
