package freelance.service.command.command.user;

import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.handler.user.ChangePasswordCommandHandler;
import freelance.service.command.utils.validation.PassWordMatch;
import freelance.service.command.utils.validation.PassWordMatcher;
import freelance.service.command.utils.validation.ValidPassword;
import freelance.service.utils.TypeUtils;
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
