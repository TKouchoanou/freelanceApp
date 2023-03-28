package freelance.service.command.command.employee;

import freelance.service.command.Command;
import freelance.service.command.Command.Usecase;
import freelance.service.command.CommandException;
import freelance.service.command.handler.employee.UpdateEmployeeCommandHandler;
import freelance.service.command.utils.validation.NotEmptyNumber;
import freelance.service.utils.TypeUtils;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
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
