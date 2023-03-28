package freelance.application.command.command.billing;

import freelance.application.command.Command;
import freelance.application.command.Command.Usecase;
import freelance.application.command.CommandException;
import freelance.application.command.handler.billing.UpdateBillingUserCommandHandler;
import freelance.application.command.utils.validation.NotEmptyNumber;
import freelance.application.utils.TypeUtils;
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
