import java.util.Scanner;

public class Imagine {

    final static int MAX_CONTAINER_COUNT = 12;
    final static int MAX_BOX_COUNT = 27;

    static int containerCount = 0;
    static int carCount = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int inputBoxCount = scanner.nextInt();

        //вычисление количества грузовиков, контейнеров и ящиков в последнем контейнере
        int boxesLeftCount = inputBoxCount % MAX_BOX_COUNT;
        containerCount = computeContainerCount(boxesLeftCount, inputBoxCount);
        carCount = computeCarCount();

        // вывод результата
        output(boxesLeftCount);
        System.out.println();
        System.out.println("Необходимо:");
        System.out.println("грузовиков - " + carCount + " шт.");
        System.out.println("контейнеров - " + containerCount + " шт.");
//        System.out.println(boxesLeftCount);
    }

    private static int computeContainerCount(int boxesLeft, int boxesAll) {
        if (boxesLeft == 0) {
            return boxesAll / MAX_BOX_COUNT;
        } else {
            return boxesAll / MAX_BOX_COUNT + 1;
        }
    }

    private static int computeCarCount() {
        if (containerCount % MAX_CONTAINER_COUNT == 0) {
            return containerCount / MAX_CONTAINER_COUNT;
        } else {
             return containerCount / MAX_CONTAINER_COUNT + 1;
        }
    }

    private static void output(int boxesLeftCount) {
        int containerBound = MAX_CONTAINER_COUNT; // верхняя граница для перебора по контейнерам
        int boxBound = MAX_BOX_COUNT; // верхняя граница для перебора по ящикам

        int jAll = 1; // для сквозной нумерации контейнеров
        int kAll = 1; // для сквозной нумерации ящиков

        // для самопроверки вывод осуществляется в форме <объект><сквозной номер><порядковый номер в объекта в машине\контейнере>
        for (int i = 1; i <= carCount; i++) {
            System.out.println("Грузовик " + i + ": ");
            if (i == carCount) {
                if (containerCount <= MAX_CONTAINER_COUNT) {
                    containerBound = containerCount;
                } else {
                    containerBound = containerCount % MAX_CONTAINER_COUNT;
                }
            }
            for (int j = 1; j <= containerBound; j++) {
                if ((i == carCount) && (j == containerBound) && (boxesLeftCount > 0)){
                    boxBound = boxesLeftCount;
                }
                System.out.println("\tКонтейнер " + jAll + " (" + j + "): ");
                for (int k = 1; k <= boxBound; k++) {
                    System.out.println("\t\tЯщик " + kAll + " (" + k + ")");
                    kAll++;
                }
                jAll++;
            }
        }
    }

}
