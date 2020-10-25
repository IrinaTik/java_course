import java.math.BigDecimal;
import java.math.RoundingMode;

public class Manager implements Employee {

    private final BigDecimal FIXED_SALARY = BigDecimal.valueOf(Math.random() * 40_000 + 60_000);
    private static final BigDecimal LOW = new BigDecimal("115000.0");
    private static final BigDecimal TOP = new BigDecimal("140000.0");
    private static final BigDecimal PERCENT = BigDecimal.valueOf(0.05);

    private Company company;

    @Override
    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public BigDecimal getMonthSalary() {
        // fixed + 5% * (random * (TOP - LOW) + LOW)
        // вывод 5 знаков после запятой с округлением вниз
        BigDecimal salary = FIXED_SALARY.add(determineMoneyInput().multiply(PERCENT));
        return salary.setScale(0, RoundingMode.DOWN);
    }

    private BigDecimal determineMoneyInput() {
        // от LOW до TOP рублей.
        // random * (TOP - LOW) + LOW
        BigDecimal randomValue = BigDecimal.valueOf(Math.random());
        BigDecimal index = TOP.subtract(LOW);
        return index.multiply(randomValue).add(LOW);
    }

}
