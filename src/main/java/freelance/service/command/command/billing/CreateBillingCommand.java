package freelance.service.command.command.billing;

import freelance.service.command.Command;
import freelance.service.command.Command.Usecase;
import freelance.service.command.CommandException;
import freelance.service.command.handler.billing.CreateBillingCommandHandler;
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
    Long userId;
    @Override
    public void validateStateBeforeHandling() {
        if(TypeUtils.hasValue(billingId)){
            throw new CommandException(" you cannot create existing billing,try billing update ");
        }
        if(!TypeUtils.hasValue(userId)){
            throw new CommandException("userId is required");
        }
        if(!TypeUtils.hasValue(ribId)){
            throw new CommandException(" ribId is required");
        }
        if(startedDate==null || endedDate==null){
            throw  new CommandException(" startedDate and endedDate are required");
        }
        if(amountHT==null || amountTTC==null){
            throw new CommandException(" amountTTC and amountHT are required");
        }
        if(amountTTC.compareTo(amountHT)<0){
            throw new CommandException("amount TTC must be greater or equals to amount HT");
        }
    }

    @Override
    public void validateStateAfterHandling() {
      if(billingId==null){
          throw new CommandException(" billingId have not been valorised");
      }
      if(validationStatus==null){
          throw new CommandException(" billing validation status have not been valorised");
      }
        if(paymentStatus==null){
            throw new CommandException(" billing payement status have not been valorised");
        }
    }
}
