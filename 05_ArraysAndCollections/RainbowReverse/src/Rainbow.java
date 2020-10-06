public class Rainbow {

    public static void main(String[] args) {
        String colors = "Каждый охотник желает знать, где сидит фазан.";

        String[] arrColor = colors.split("[\\s,.]+");
        System.out.println("Original order:");
        for (String color : arrColor) {
            System.out.println(color);
        }

        for (int i = 0; i < (arrColor.length - i - 1); i++) {
            String temp = arrColor[i];
            arrColor[i] = arrColor[arrColor.length - i - 1];
            arrColor[arrColor.length - i - 1] = temp;
        }

        System.out.println("==============");
        System.out.println("Reverse order:");
        for (String color : arrColor) {
            System.out.println(color);
        }
    }

}
