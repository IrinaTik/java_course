public class Secret {

    public static void main(String[] args) {
        String safe = searchAndReplaceDiamonds("Номер кредитной карты <4008 1234 5678> 8912", "***");
        System.out.println(safe);
    }

    private static String searchAndReplaceDiamonds(String text, String placeholder) {
        int fIndex = text.indexOf("<");
        int lIndex = text.indexOf(">");
        String subStr = text.substring(fIndex, lIndex + 1);
        return text.replace(subStr, placeholder);
    }

}
