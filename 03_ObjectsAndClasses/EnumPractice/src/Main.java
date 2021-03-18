/*
Создайте класс ArithmeticCalculator, который будет выполнять арифметические операции над числами.
Реализуйте конструктор с двумя параметрами-числами, который сохраняет переданные числа в свойствах класса.
Реализуйте метод calculate, на вход которого передаётся одна из арифметических операций (объект класса Operation —
    смотрите следующий пункт задания), которую нужно произвести с двумя числами, переданными в конструктор при создании объекта.
Создайте Enum Operation с тремя значениями: ADD, SUBTRACT, MULTIPLY. Эти значения будут соответствовать арифметическим операциям — сложению, вычитанию и умножению.
 */

public class Main {

    public static void main(String[] args) {
        ArithmeticCalculator calculator = new ArithmeticCalculator(0.0, 5.5);
        System.out.println(calculator.calculate(Operation.ADD));
        System.out.println(calculator.calculate(Operation.SUBTRACT));
        System.out.println(calculator.calculate(Operation.MULTIPLY));
    }
}
