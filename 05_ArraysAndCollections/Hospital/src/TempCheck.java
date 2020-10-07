public class TempCheck {

    private static final int LENGTH = 30;

    public static void main(String[] args) {
        float[] temperatures = fillArray(LENGTH);
        output(temperatures);

        float sum = 0;
        int healthyPeopleNumber = 0;
        System.out.print("Температуры здоровых пациентов: ");
        for (float temperature : temperatures) {
            sum += temperature;
            if ((temperature >= 36.2) && (temperature <= 36.9)) {
                System.out.printf("%.1f ", temperature);
                healthyPeopleNumber++;
            }
        }
        if (healthyPeopleNumber == 0) {
            System.out.print("нет здоровых пациентов!");
        }
        System.out.println();
        System.out.printf("Средняя температура: %.2f ", sum / temperatures.length);
        System.out.println();
        System.out.println("Количество здоровых:  " + healthyPeopleNumber);
    }

    private static float[] fillArray(int length) {
        float[] array = new float[length];
        for (int i = 0; i < array.length; i++) {
            float temp = 32 + (float) (10 * Math.random());
            array[i] = (temp > 40 ? temp - 2 : temp);
        }
        return array;
    }

    private static void output (float[] array) {
        for (float f : array) {
            System.out.printf("%.1f", f);
            System.out.println();
        }
    }

}
