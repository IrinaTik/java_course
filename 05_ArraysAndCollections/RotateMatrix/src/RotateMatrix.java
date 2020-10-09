import java.util.Scanner;

public class RotateMatrix {

    public static void main(String[] args) {

        int[][] matrix = input();
        System.out.println("===============");
        System.out.println("Original matrix");
        output(matrix);
        System.out.println("===============");
        System.out.println("Rotated matrix");
        output(rotate(matrix));
    }

    private static int[][] rotate(int[][] matrix){
        for (int i = 0; i <= matrix.length - 2; i++) {
            for (int j = i; j <= matrix.length - 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[matrix.length - j - 1][i];
                matrix[matrix.length - j - 1][i] = matrix[matrix.length - i - 1][matrix.length - j - 1];
                matrix[matrix.length - i - 1][matrix.length - j - 1] = matrix[j][matrix.length - i - 1];
                matrix[j][matrix.length - i - 1] = temp;
            }
        }
        return matrix;
    }

    private static int[][] input() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input N: ");
        int n = scanner.nextInt();
        int[][] matrix = new int[n][n];

        System.out.println("Input the matrix elements (row by row)");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print("Element [" + i + ", " + j + "]: ");
                matrix[i][j] = scanner.nextInt();
            }
        }
        return matrix;
    }

    private static void output(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
