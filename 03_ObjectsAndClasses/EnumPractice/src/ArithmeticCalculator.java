public class ArithmeticCalculator {

    private final double firstDigit;
    private final double secondDigit;

    public ArithmeticCalculator(double firstDigit, double secondDigit) {
        this.firstDigit = firstDigit;
        this.secondDigit = secondDigit;
    }

    public double calculate(Operation type) {
        switch (type) {
            case ADD: return firstDigit + secondDigit;
            case SUBTRACT: return firstDigit - secondDigit;
            case MULTIPLY: return firstDigit * secondDigit;
            default: return Double.MAX_VALUE;
        }
    }
}
