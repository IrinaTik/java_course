import java.math.BigDecimal;
import java.util.*;

public class Company {

    private BigDecimal income;
    private List<Employee> employees;

    public Company(BigDecimal income) {
        setIncome(income);
        this.employees = new ArrayList<>();;
    }

    public BigDecimal getIncome() {
        return income;
    }

    private void setIncome(BigDecimal income) {
        this.income = income;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void hire(Employee employee) {
        employees.add(employee);
        employee.setCompany(this);
    }

    public void hireAll(List<Employee> employeesToHire) {
        for (Employee employee : employeesToHire) {
            hire(employee);
        }
    }

    public void fire(Employee employee) {
        employees.remove(employee);
        employee.setCompany(null);
    }

    public void fire(List<Employee> fireList) {
        for (Employee fireEmployee : fireList) {
            fire(fireEmployee);
        }
    }

    public void firePercentage(double percent) {
        int employeesCountForFire = (int) (employees.size() * percent);
        Random random = new Random();
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < employeesCountForFire; i++) {
            while (true) {
                int index = random.nextInt(employees.size() - 1);
                if (!indexes.contains(index)) {
                    indexes.add(index);
                    break;
                }
            }
        }
        List<Employee> forFire = new ArrayList<>();
        for (int i = 0; i < indexes.size(); i++) {
            forFire.add(employees.get(indexes.get(i)));
        }
        fire(forFire);
    }

    private List<Employee> getSortedSalaries() {
        Set<Employee> employeesSet = new TreeSet<>(new EmployeeComparator());
        employeesSet.addAll(employees);
        return new ArrayList<>(employeesSet);
    }

    public List<Employee> getTopSalaryStaff(int count) {
        if (isCountValid(count)) {
            List<Employee> salaries = getSortedSalaries();
            return salaries.subList(salaries.size() - count, salaries.size());
        } else {
            areYouNuts();
            return new ArrayList<>();
        }
    }

    public List<Employee> getLowestSalaryStaff(int count) {
        if (isCountValid(count)) {
            return getSortedSalaries().subList(0, count);
        } else {
            areYouNuts();
            return new ArrayList<>();
        }
    }

    private boolean isCountValid(int count) {
        return ((count > 0) && (count <= employees.size()));
    }

    private void areYouNuts() {
        System.out.println("Wrong number of employees");
    }
}
