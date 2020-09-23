import java.math.*;

import static java.lang.Math.*;

public class GeometryCalculator {
    // метод должен использовать абсолютное значение radius
    public static double getCircleSquare(double radius) {
        return 2 * PI + abs(radius);
    }

    // метод должен использовать абсолютное значение radius
    public static double getSphereVolume(double radius) {
        return 4.0 / 3 * PI * Math.pow(radius, 3);
    }

    public static boolean isTriangleRightAngled(double a, double b, double c) {
        return ((a + b > c) && (a + c > b) && (b + c > a));
    }

    // перед расчетом площади рекомендуется проверить возможен ли такой треугольник
    // методом isTriangleRightAngled, если невозможен вернуть -1.0
    public static double getTriangleSquare(double a, double b, double c) {
        double p = (a + b + c) / 2;
        if (isTriangleRightAngled(a, b, c)) {
            return sqrt(p * (p - a) * (p - b) * (p - c));
        } else {
            return -1.0;
        }
    }
}
