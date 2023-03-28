package freelance.application.command.command.employee;
import freelance.application.command.Command.Usecase;
import freelance.application.command.Command;
import freelance.application.command.CommandException;
import freelance.application.command.handler.employee.CreateEmployeeCommandHandler;
import freelance.application.command.utils.validation.NotEmptyNumber;
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
