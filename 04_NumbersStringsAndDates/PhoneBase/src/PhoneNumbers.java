public class PhoneNumbers {

    public static void main(String[] args) {
        String phoneNumber = "8-905-12345672342";

        String formattedPhone = phoneNumber.replaceAll("[^0-9]", "");

        if (formattedPhone.length() == 10) {
            formattedPhone = "7" + formattedPhone;
        } else {
            if (formattedPhone.length() == 11) {
                if (formattedPhone.charAt(0) == '8') {
                    formattedPhone = formattedPhone.replaceFirst("8", "7");
                }
            } else {
                formattedPhone = "Неверный формат номера";
            }
        }

        System.out.println(formattedPhone);
    }

}
