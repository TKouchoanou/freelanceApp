package freelance.service.command.command.billing;

import freelance.service.command.Command;
import freelance.service.command.Command.Usecase;
import freelance.service.command.CommandException;
import freelance.service.command.handler.billing.CreateBillingCommandHandler;
import freelance.service.command.utils.validation.NotEmptyNumber;
import freelance.service.utils.TypeUtils;
import lombok.*;
import lombok.experimental.SuperBuilder;






@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Usecase(handlers = {CreateBillingCommandHandler.class})
public class CreateBillingCommand extends AbstractBillingCommand implements Command{
    @NotEmptyNumber
    Long billingId;
    @NotEmptyNumber
    Long userId;
    @Override
    public void validateStateBeforeHandling() {
        if(!TypeUtils.hasValue(userId)){
            throw new CommandException("userId is required");
        }
        super.commonValidateStateBeforeHandling();
    }

    @Override
    public void validateStateAfterHandling() {
      if(billingId==null){
          throw new CommandException(" billingId have not been valorise");
      }
      if(validationStatus==null){
          throw new CommandException(" billing validation status have not been valorise");
      }
        if(paymentStatus==null){
            throw new CommandException(" billing payement status have not been valorise");
        }
    }
}
