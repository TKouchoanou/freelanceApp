package freelance.service.command.handler.billing;

import freelance.domain.models.entity.Billing;
import freelance.domain.models.objetValue.BillingId;
import freelance.domain.models.objetValue.ValidationStatus;
import freelance.domain.repository.BillingRepository;
import freelance.domain.repository.CompanyRepository;
import freelance.domain.repository.RibRepository;
import freelance.domain.repository.UserRepository;
import freelance.domain.security.Auth;
import freelance.service.command.Command;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.billing.UpdateValidationStatusCommand;
import freelance.service.command.utils.AuthProvider;
import org.springframework.stereotype.Service;
import freelance.service.command.Command.Usecase;

@Service
public class UpdateValidationStatusCommandHandler implements CommandHandler {
    AuthProvider authProvider;
    BillingRepository billingRepository;
    UpdateValidationStatusCommandHandler(  BillingRepository billingRepository,
                                  AuthProvider authProvider){
        this.billingRepository=billingRepository;
        this.authProvider=authProvider;
    }
    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof UpdateValidationStatusCommand cmd)){
            return;
        }
        Billing billing = billingRepository.getById(new BillingId(cmd.getBillingId()));
        Auth auth= authProvider.getCurrentAuth();
        billing.updateValidateStatus(ValidationStatus.valueOf(cmd.getValidationStatus()),auth);
        billingRepository.save(billing);
    }
}
