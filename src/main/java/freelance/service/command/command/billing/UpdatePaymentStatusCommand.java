package freelance.service.command.command.billing;

import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.handler.billing.UpdatePaymentStatusCommandHandler;
import freelance.service.utils.TypeUtils;
import freelance.service.command.Command.Usecase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter

@Usecase(handlers = {UpdatePaymentStatusCommandHandler.class})
public class UpdatePaymentStatusCommand implements Command {
    Long billingId;
    String paymentStatus;
    @Override
    public void validateStateBeforeHandling() {
        if(!TypeUtils.hasValue(billingId)){
            throw new CommandException(" you cannot update billing without provide billing Id");
        }
        freelance.domain.models.objetValue.ValidationStatus.valueOf(paymentStatus);
    }

    @Override
    public void validateStateAfterHandling() {

    }
}
