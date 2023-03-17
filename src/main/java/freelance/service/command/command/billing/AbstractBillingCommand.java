package freelance.service.command.command.billing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class AbstractBillingCommand  {
    Long billingId;
    Long ribId;
    Long companyId;
    LocalDate startedDate;
    LocalDate endedDate;
    BigDecimal amountTTC;
    BigDecimal amountHT;
    byte[] file;
    String fileName;
}
