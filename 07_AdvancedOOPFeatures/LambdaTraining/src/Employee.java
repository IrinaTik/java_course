import java.math.BigDecimal;

public class Employee {

    private String name;
    private BigDecimal salary;

    public Employee() {
        Generator generator = new Generator();
        setName(generator);
        setSalary(generator);
    }

    public String getName() {
        return name;
    }

    private void setName(Generator generator) {
        this.name = generator.generateName();
    }

    public BigDecimal getSalary() {
        return salary;
    }

    private void setSalary(Generator generator) {
        this.salary = generator.generateSalary();
    }

    @Override
    public String toString() {
        return salary + " руб - " + name;
    }
}
