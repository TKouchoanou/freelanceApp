package freelance.service.command.command.user;

import freelance.domain.exception.DomainException;
import freelance.domain.models.objetValue.EmployeeRole;
import freelance.domain.models.objetValue.Profile;
import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.handler.user.CreateUserCommandHandler;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;


@SuperBuilder
@Getter
@Command.Usecase(handlers = CreateUserCommandHandler.class)
public class CreateUserCommand extends AbstractUserCommand{
    String passWord;
    @Override
    public void validateStateBeforeHandling() {
        if(id!=null){
            throw new CommandException("invalid create command with not null id");
        }
    }

    @Override
    public void validateStateAfterHandling() {
        if(id==null){
            throw new CommandException("value id can be null after handling");
        }
    }
}
