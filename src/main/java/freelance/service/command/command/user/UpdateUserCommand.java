package freelance.service.command.command.user;

import freelance.service.command.CommandException;
import freelance.service.command.handler.user.UpdateUserCommandHandler;
import freelance.service.command.utils.validation.NotEmptyNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static freelance.service.command.Command.Usecase;


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
