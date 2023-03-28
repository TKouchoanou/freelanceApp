package freelance.service.command.command.employee;
import freelance.service.command.Command.Usecase;
import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.handler.employee.CreateEmployeeCommandHandler;
import freelance.service.command.utils.validation.NotEmptyNumber;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Usecase(handlers = {CreateEmployeeCommandHandler.class})
public class CreateEmployeeCommand implements Command {
    @NotEmptyNumber
    Long userId;
    List<String> roles;
    Long employeeId;


    @Override
    public void validateStateBeforeHandling() {
     if(userId==null){
         throw  new CommandException("null user id provided to create employee");
     }
    }

    @Override
    public void validateStateAfterHandling() {
        if(employeeId==null){
            throw  new CommandException("null user id provided to create employee");
        }
    }
}
