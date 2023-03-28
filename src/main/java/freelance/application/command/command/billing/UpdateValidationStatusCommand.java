package freelance.application.command.command.billing;

import freelance.domain.core.objetValue.ValidationStatus;
import freelance.application.command.Command;
import freelance.application.command.Command.Usecase;
import freelance.application.command.CommandException;
import freelance.application.command.handler.billing.UpdateValidationStatusCommandHandler;
import freelance.application.command.utils.validation.NotEmptyNumber;
import freelance.application.utils.TypeUtils;
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
@Usecase(handlers = {UpdateValidationStatusCommandHandler.class})
public class UpdateValidationStatusCommand implements Command {
    @NotEmptyNumber
    Long billingId;
    String validationStatus;
    @Override
    public void validateStateBeforeHandling() {
        if(!TypeUtils.hasValue(billingId)){
            throw new CommandException(" you cannot update billing without provide billing id");
        }
        ValidationStatus.valueOf(validationStatus);
    }

    @Override
    public void validateStateAfterHandling() {

    }
}
