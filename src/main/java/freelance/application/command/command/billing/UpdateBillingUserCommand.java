package freelance.application.command.command.billing;

import freelance.application.command.Command;
import freelance.application.command.Command.Usecase;
import freelance.application.command.CommandException;
import freelance.application.command.handler.billing.UpdateBillingUserCommandHandler;
import freelance.application.command.utils.validation.NotEmptyNumber;
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
    @NotEmptyNumber
    Long billingId;
    Long userId;
    @Override
    public void validateStateBeforeHandling() {
        if(billingId==null){
            throw new CommandException("freelance id is not provided");
        }
        if(userId==null){
            throw new CommandException("user id is not provided");
        }

    }

    @Override
    public void validateStateAfterHandling() {

    }
}
