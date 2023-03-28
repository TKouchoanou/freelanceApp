package freelance.service.command.command.user;

import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.handler.user.CreateUserCommandHandler;
import freelance.service.command.utils.validation.PassWordMatch;
import freelance.service.command.utils.validation.PassWordMatcher;
import freelance.service.command.utils.validation.UniqueEmail;
import freelance.service.command.utils.validation.ValidPassword;
import freelance.service.utils.TypeUtils;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Command.Usecase(handlers = CreateUserCommandHandler.class)
@PassWordMatch
public class CreateUserCommand extends AbstractUserCommand implements PassWordMatcher {
    Long id;
    @UniqueEmail
    @Email //TODO utiliser une custom annotation utilisant le common validator utilisé par object value Email pour harmoniser partout la meme regle de validation
    String email;
    @ValidPassword
    String passWord;
    @ValidPassword
    String passWordConfirmation;


    public boolean isPasswordsMatch() {
        return TypeUtils.hasValue(passWord) && passWord.equals(passWordConfirmation);
    }
    @Override
    public void validateStateBeforeHandling() {
        if(id!=null){
            throw new CommandException("invalid create command with not null id");
        }
        if(passWord!=null && !passWord.equals(passWordConfirmation)){
            throw new CommandException("The password and confirmation password must match");
        }
    }

    @Override
    public void validateStateAfterHandling() {
        if(id==null){
            throw new CommandException("value id can be null after handling");
        }
    }
}
