import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int TOPMANAGER_COUNT = 10;
    private static final int MANAGER_COUNT = 80;
    private static final int OPERATOR_COUNT = 180;

    public static void main(String[] args) {
        Company company = new Company(BigDecimal.valueOf(12_000_000));
        company.hireAll(generateOperators(company));
        company.hireAll(generateManagers(company));
        company.hireAll(generateTopManagers(company));

        printResults(company);
        company.firePercentage(0.5);
        printResults(company);
    }

    private static void printResults(Company company){
        System.out.println("=============");
        System.out.println("Top salaries");
        output(company.getTopSalaryStaff(-10));
        System.out.println("=============");
        System.out.println("Low salaries");
        output(company.getLowestSalaryStaff(30));
    }

    private static void output (List<Employee> employees) {
        for (Employee employee : employees) {
            System.out.println(employee.getMonthSalary() + " руб");
        }
    }

    private static List<Employee> generateOperators(Company company) {
        List<Employee> operators = new ArrayList<>();
        for (int i = 0; i < OPERATOR_COUNT; i++) {
            operators.add(new Operator());
        }
//        System.out.println("Operators");
//        output(operators);
//        System.out.println("=============");
        return operators;
    }

    private static List<Employee> generateManagers(Company company) {
        List<Employee> managers = new ArrayList<>();
        for (int i = 0; i < MANAGER_COUNT; i++) {
            managers.add(new Manager());
        }
//        System.out.println("Managers");
//        output(managers);
//        System.out.println("=============");
        return managers;
    }

    private static List<Employee> generateTopManagers(Company company) {
        List<Employee> topmanagers = new ArrayList<>();
        for (int i = 0; i < TOPMANAGER_COUNT; i++) {
            topmanagers.add(new TopManager(company));
        }
//        System.out.println("TopManagers");
//        output(topmanagers);
//        System.out.println("=============");
        return topmanagers;
    }

    private static void salaryTest() {
        Company company = new Company(BigDecimal.valueOf(12_000_000));
        Employee topmanager = new TopManager(company);
        System.out.println("TopManager " + topmanager.getMonthSalary());
        Employee manager = new Manager();
        System.out.println("Manager " + manager.getMonthSalary());
        Employee operator = new Operator();
        System.out.println("Operator " + operator.getMonthSalary());
    }

}
