package freelance.service.command.command.billing;

import freelance.service.command.CommandException;
import freelance.service.utils.TypeUtils;
import jakarta.validation.constraints.NotNull;
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

    Long ribId;
    Long companyId;
    @NotNull
    LocalDate startedDate;
    @NotNull
    LocalDate endedDate;
    @NotNull
    BigDecimal amountTTC;
    @NotNull
    BigDecimal amountHT;
    byte[] file;
    String fileName;
    String validationStatus;
    String paymentStatus;
     void commonValidateStateBeforeHandling() {

        if(!TypeUtils.hasValue(ribId)){
            throw new CommandException(" ribId is required");
        }
        if(startedDate==null || endedDate==null){
            throw  new CommandException(" startedDate and endedDate are required");
        }
        if(amountHT==null || amountTTC==null){
            throw new CommandException(" amountTTC and amountHT are required");
        }
        if(amountTTC.compareTo(amountHT)<0){
            throw new CommandException("amount TTC must be greater or equals to amount HT");
        }
    }
}
