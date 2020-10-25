import java.math.BigDecimal;
import java.math.RoundingMode;

public class TopManager implements Employee {

    private final BigDecimal FIXED_SALARY = BigDecimal.valueOf(Math.random() * 100_000 + 100_000);
    private static final BigDecimal PERCENT = BigDecimal.valueOf(1.5);
    private static final BigDecimal BONUS_INCOME = BigDecimal.valueOf(10_000_000);

    private Company company;

    public TopManager(Company company) {
        setCompany(company);
    }

    @Override
    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public BigDecimal getMonthSalary() {
        return FIXED_SALARY.add(bonus()).setScale(0, RoundingMode.DOWN);
    }

    private BigDecimal bonus() {
        if (company.getIncome().compareTo(BONUS_INCOME) == 1) {
            return FIXED_SALARY.multiply(PERCENT);
        }
        return BigDecimal.ZERO;
    }

}
