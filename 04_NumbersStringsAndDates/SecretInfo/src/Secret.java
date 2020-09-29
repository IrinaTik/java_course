public class Secret {

    public static void main(String[] args) {
        String safe = searchAndReplaceDiamondsRegex("Номер кредитной карты <4008 1234 5678> 8912 <123> 5678 <9999> 2", "***");
        System.out.println(safe);
    }

    private static String searchAndReplaceDiamonds(String text, String placeholder) {
        int fIndex = text.indexOf("<");
        int lIndex = text.indexOf(">");
        String subStr = text.substring(fIndex, lIndex + 1);
        return text.replace(subStr, placeholder);
    }

    private static String searchAndReplaceDiamondsRegex(String text, String placeholder) {
        return text.replaceAll("<.+?>", placeholder);
    }

}
