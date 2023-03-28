package freelance.service.command.command.billing;

import freelance.service.command.Command;
import freelance.service.command.Command.Usecase;
import freelance.service.command.CommandException;
import freelance.service.command.handler.billing.UpdateBillingUserCommandHandler;
import freelance.service.command.utils.validation.NotEmptyNumber;
import freelance.service.utils.TypeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
//@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Usecase(handlers = {UpdateBillingUserCommandHandler.class})
public class UpdateBillingCommand extends AbstractBillingCommand implements Command {
    @NotEmptyNumber
    Long billingId;
    @Override
    public void validateStateBeforeHandling() {
        if(!TypeUtils.hasValue(billingId)){
            throw new CommandException(" you cannot update billing without provide billing id");
        }
     super.commonValidateStateBeforeHandling();
    }

    @Override
    public void validateStateAfterHandling() {
    }
}
