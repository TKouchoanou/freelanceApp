package freelance.application.command.command.user;

import freelance.application.command.CommandException;
import freelance.application.command.handler.user.UpdateUserCommandHandler;
import freelance.application.command.utils.validation.NotEmptyNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static freelance.application.command.Command.Usecase;


@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@Usecase(handlers = UpdateUserCommandHandler.class)
public class UpdateUserCommand extends AbstractUserCommand{
   @NotEmptyNumber
    Long id;
    @NotBlank
    @Email
    String email;
    public void setIsActive(boolean isActive) {
        super.setActive(isActive);
    }

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
    public boolean getIsActive() {
        return isActive;
    }
}
