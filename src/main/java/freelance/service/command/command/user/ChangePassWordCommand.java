package freelance.service.command.command.user;

import freelance.service.command.Command;
import freelance.service.command.CommandException;
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
public class ChangePassWordCommand implements Command {
    Long userId;
    String newPassWord;
    String newPassWordConfirmation;
    Boolean succeed;

    public String getNewPassWord() {
        return newPassWord;
    }

    public String getNewPassWordConfirmation() {
        return newPassWordConfirmation;
    }

    public Long getUserId() {
        return userId;
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    @Override
    public void validateStateBeforeHandling() {
        if(this.newPassWord==null ){
            throw new CommandException(" null pass word not allowed");
        }
        if(this.newPassWord.isBlank()){
            throw new CommandException(" blank pass word not allowed");
        }
       if( this.newPassWord.equals(this.newPassWordConfirmation)){
           throw new CommandException(" password confirmation and password are not similar");
       }

    }

    @Override
    public void validateStateAfterHandling() {
        if (!this.succeed){
            throw new CommandException("failed to update pass word");
        }
    }
}
