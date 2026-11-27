package decorator.component;

import java.math.BigDecimal;

public interface SalaryComponent {
    BigDecimal calculateSalary();
    BigDecimal getBaseSalary();
    String getDescription();
}
