package freelance.application.command.command.employee;

import freelance.application.command.Command;
import freelance.application.command.Command.Usecase;
import freelance.application.command.CommandException;
import freelance.application.command.handler.employee.UpdateEmployeeCommandHandler;
import freelance.application.command.utils.validation.NotEmptyNumber;
import freelance.application.utils.TypeUtils;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Usecase(handlers = {UpdateEmployeeCommandHandler.class})
public class UpdateEmployeeCommand implements Command {
    Set<String> roles;
    @NotEmptyNumber
    Long employeeId;
    @Override
    public void validateStateBeforeHandling() {
        if(!TypeUtils.hasValue(employeeId)){
            throw new CommandException("cannot update employee if employeeId is not specified ");
        }

    }

    @Override
    public void validateStateAfterHandling() {

    }
}
