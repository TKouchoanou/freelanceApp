package freelance.application.command.command.freeLance;

import freelance.application.command.Command;
import freelance.application.command.CommandException;
import freelance.application.command.handler.freeLance.UpdateFreeLanceCommandHandler;
import freelance.application.command.utils.validation.NotEmptyNumber;
import freelance.application.utils.TypeUtils;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Command.Usecase(handlers = UpdateFreeLanceCommandHandler.class)
public class UpdateFreeLanceCommand implements Command {
    Long Id;
    @NotEmptyNumber
    Long ribId;
    @NotEmptyNumber
    Long companyId;
    @Override
    public void validateStateBeforeHandling() {
        if(!TypeUtils.hasValue(Id)){
            throw new CommandException("cannot update freelance if employeeId is not specified");
        }
    }

    @Override
    public void validateStateAfterHandling() {

    }
}
