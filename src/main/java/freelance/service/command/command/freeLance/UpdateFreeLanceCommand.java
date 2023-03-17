package freelance.service.command.command.freeLance;

import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.utils.TypeUtils;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateFreeLanceCommand implements Command {
    Long ribId;
    Long freeLanceId;
    Long companyId;
    @Override
    public void validateStateBeforeHandling() {
        if(!TypeUtils.hasValue(freeLanceId)){
            throw new CommandException("cannot update freelance if employeeId is not specified");
        }
    }

    @Override
    public void validateStateAfterHandling() {

    }
}
