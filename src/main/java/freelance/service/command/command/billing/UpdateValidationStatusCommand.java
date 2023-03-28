package freelance.service.command.command.billing;

import freelance.domain.models.objetValue.ValidationStatus;
import freelance.service.command.Command;
import freelance.service.command.Command.Usecase;
import freelance.service.command.CommandException;
import freelance.service.command.handler.billing.UpdateValidationStatusCommandHandler;
import freelance.service.command.utils.validation.NotEmptyNumber;
import freelance.service.utils.TypeUtils;
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
