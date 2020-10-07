public class MassiveX {

    public static void main(String[] args) {

        String[][] array = new String[7][7];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if ((i == j) || (i == array[i].length - j - 1)) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

    }

}
