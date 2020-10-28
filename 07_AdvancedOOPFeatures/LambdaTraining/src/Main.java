import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final int OPERATOR_COUNT = 50;

    public static void main(String[] args) {
        List<Employee> employees = generateEmployees();
//        output(employees);

        Collections.sort(employees, (e1, e2) -> {
            int salaryComparison = e1.getSalary().compareTo(e2.getSalary());
            int nameComparison = e1.getName().compareTo(e2.getName());
            if (salaryComparison == 0) {
                return nameComparison;
            } else {
                return salaryComparison;
            }
        });

        System.out.println("======Sorted======");
        output(employees);
    }

    private static void output (List<Employee> employees) {
        for (Employee employee : employees) {
            System.out.println(employee.toString());
        }
    }

    private static List<Employee> generateEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        for (int i = 0; i < OPERATOR_COUNT; i++) {
            employeeList.add(new Employee());
        }
//        System.out.println("Operators");
//        output(operators);
//        System.out.println("=============");
        return employeeList;
    }

}
