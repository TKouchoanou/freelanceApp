package freelance.service.command.command.freeLance;

import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.handler.freeLance.UpdateFreeLanceCommandHandler;
import freelance.service.command.utils.validation.NotEmptyNumber;
import freelance.service.utils.TypeUtils;
import jakarta.validation.constraints.NotNull;
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
