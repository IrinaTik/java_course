import java.math.BigDecimal;
import java.math.RoundingMode;

public class Operator implements Employee {

    private final BigDecimal FIXED_SALARY = BigDecimal.valueOf(Math.random() * 20_000 + 30_000);

    private Company company;

    @Override
    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public BigDecimal getMonthSalary() {
        return FIXED_SALARY.setScale(0, RoundingMode.DOWN);
    }

}
