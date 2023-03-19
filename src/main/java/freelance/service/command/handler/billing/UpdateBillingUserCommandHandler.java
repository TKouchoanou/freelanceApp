package freelance.service.command.handler.billing;

import freelance.domain.models.entity.Billing;
import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.BillingId;
import freelance.domain.models.objetValue.UserId;
import freelance.domain.repository.BillingRepository;
import freelance.domain.repository.CompanyRepository;
import freelance.domain.repository.RibRepository;
import freelance.domain.repository.UserRepository;
import freelance.domain.security.Auth;
import freelance.service.command.Command;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.billing.UpdateBillingUserCommand;
import freelance.service.command.utils.AuthProvider;
import org.springframework.stereotype.Service;
import freelance.service.command.Command.Usecase;

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
