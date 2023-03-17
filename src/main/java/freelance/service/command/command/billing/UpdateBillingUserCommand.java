package freelance.service.command.command.billing;

import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.Command.Usecase;
import freelance.service.command.handler.billing.UpdateBillingUserCommandHandler;
import freelance.service.command.handler.billing.UpdateValidationStatusCommandHandler;
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

@Usecase(handlers = {UpdateBillingUserCommandHandler.class})
public class UpdateBillingUserCommand implements Command {
    Long billingId;
    Long userId;
    @Override
    public void validateStateBeforeHandling() {
        if(billingId==null){
            throw new CommandException("freelance Id is not provided");
        }
        if(userId==null){
            throw new CommandException("user id is not provided");
        }

    }

    @Override
    public void validateStateAfterHandling() {

    }
}
