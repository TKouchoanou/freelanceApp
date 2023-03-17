package freelance.service.command.command.user;

import freelance.service.command.CommandException;
import freelance.service.command.handler.user.UpdateUserCommandHandler;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import static freelance.service.command.Command.Usecase;


@SuperBuilder
@Getter
@Usecase(handlers = UpdateUserCommandHandler.class)
public class UpdateUserCommand extends AbstractUserCommand{
    @Override
    public void validateStateBeforeHandling() {
        if(id==null){
            throw new CommandException("invalid update command with  null id");
        }
    }

    @Override
    public void validateStateAfterHandling() {
        if(id==null){
            throw new CommandException("value id can be null after handling");
        }
    }
}
