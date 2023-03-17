package freelance.service.command.command.employee;

import freelance.service.command.Command;
import freelance.service.command.Command.Usecase;
import freelance.service.command.CommandException;
import freelance.service.command.handler.employee.UpdateEmployeeCommandHandler;
import freelance.service.utils.TypeUtils;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Usecase(handlers = {UpdateEmployeeCommandHandler.class})
public class UpdateEmployeeCommand implements Command {
    List<String> roles;
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
