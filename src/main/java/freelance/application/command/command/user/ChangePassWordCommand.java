package freelance.application.command.command.user;

import freelance.application.command.Command;
import freelance.application.command.CommandException;
import freelance.application.command.handler.user.ChangePasswordCommandHandler;
import freelance.application.command.utils.validation.PassWordMatch;
import freelance.application.command.utils.validation.PassWordMatcher;
import freelance.application.command.utils.validation.ValidPassword;
import freelance.application.utils.TypeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Command.Usecase(handlers = ChangePasswordCommandHandler.class)
@PassWordMatch
public class ChangePassWordCommand implements Command, PassWordMatcher {
    Long userId;
    @ValidPassword
    String newPassword;
    @ValidPassword
    String newPasswordConfirmation;
    Boolean succeed;

    public Boolean hasSucceed() {
        return succeed;
    }


    @Override
    public void validateStateBeforeHandling() {
        if(this.newPassword ==null ){
            throw new CommandException(" null pass word not allowed");
        }
        if(this.newPassword.isBlank()){
            throw new CommandException(" blank pass word not allowed");
        }
       if(!this.newPassword.equals(this.newPasswordConfirmation)){
           throw new CommandException(" password confirmation and password are not similar");
       }

    }

    @Override
    public void validateStateAfterHandling() {
        if (!this.succeed){
            throw new CommandException("failed to update pass word");
        }
    }

    @Override
    public boolean isPasswordsMatch() {
        return TypeUtils.hasValue(newPassword) && newPassword.equals(newPasswordConfirmation);
    }
}
