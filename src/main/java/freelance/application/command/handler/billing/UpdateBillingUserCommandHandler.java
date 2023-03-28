package freelance.application.command.handler.billing;

import freelance.domain.core.entity.Billing;
import freelance.domain.core.entity.User;
import freelance.domain.core.objetValue.BillingId;
import freelance.domain.core.objetValue.UserId;
import freelance.domain.output.repository.BillingRepository;
import freelance.domain.output.repository.UserRepository;
import freelance.domain.common.security.Auth;
import freelance.application.command.Command;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.billing.UpdateBillingUserCommand;
import freelance.application.command.utils.AuthProvider;
import org.springframework.stereotype.Service;

@Service
public class UpdateBillingUserCommandHandler implements CommandHandler {

    BillingRepository billingRepository;
    UserRepository userRepository;
    AuthProvider authProvider;
    UpdateBillingUserCommandHandler(BillingRepository billingRepository,
                                  UserRepository userRepository,
                                  AuthProvider authProvider){
        this.billingRepository=billingRepository;
        this.userRepository=userRepository;
        this.authProvider=authProvider;
    }
    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof UpdateBillingUserCommand cmd)){
            return;
        }
        UserId userId=new UserId(cmd.getUserId());
        BillingId billingId=new BillingId(cmd.getBillingId());
        Billing billing=billingRepository.getById(billingId);
        User user=userRepository.getById(userId);
        User old =userRepository.getById(billing.getUserId());
        Auth auth= authProvider.getCurrentAuth();
        billing.changeUser(old,user,auth);
        billingRepository.save(billing);
    }
}
