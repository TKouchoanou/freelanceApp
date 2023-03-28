package freelance.service.command.command.billing;

import freelance.domain.models.objetValue.PaymentStatus;
import freelance.domain.models.objetValue.ValidationStatus;
import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.handler.billing.UpdatePaymentStatusCommandHandler;
import freelance.service.command.utils.validation.NotEmptyNumber;
import freelance.service.utils.TypeUtils;
import freelance.service.command.Command.Usecase;
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
        freelance.domain.models.objetValue.PaymentStatus.valueOf(paymentStatus);
    }

    @Override
    public void validateStateAfterHandling() {

    }
}
