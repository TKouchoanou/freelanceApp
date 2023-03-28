package freelance.application.command.command.billing;

import freelance.domain.core.objetValue.PaymentStatus;
import freelance.domain.core.objetValue.ValidationStatus;
import freelance.application.command.Command;
import freelance.application.command.CommandException;
import freelance.application.command.handler.billing.UpdatePaymentStatusCommandHandler;
import freelance.application.command.utils.validation.NotEmptyNumber;
import freelance.application.utils.TypeUtils;
import freelance.application.command.Command.Usecase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter

@Usecase(handlers = {UpdatePaymentStatusCommandHandler.class})
public class UpdatePaymentStatusCommand implements Command {
    @NotEmptyNumber
    Long billingId;
    String paymentStatus;
    @Override
    public void validateStateBeforeHandling() {
        if(!TypeUtils.hasValue(billingId)){
            throw new CommandException(" you cannot update billing without provide billing id");
        }
        Arrays.stream(ValidationStatus.values()).forEach(System.out::println);
        Arrays.stream(PaymentStatus.values()).forEach(System.out::println);
        freelance.domain.core.objetValue.PaymentStatus.valueOf(paymentStatus);
    }

    @Override
    public void validateStateAfterHandling() {

    }
}
