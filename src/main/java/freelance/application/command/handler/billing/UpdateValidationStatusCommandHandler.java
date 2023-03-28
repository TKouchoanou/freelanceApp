package freelance.application.command.handler.billing;

import freelance.domain.core.entity.Billing;
import freelance.domain.core.objetValue.BillingId;
import freelance.domain.core.objetValue.ValidationStatus;
import freelance.domain.output.repository.BillingRepository;
import freelance.domain.common.security.Auth;
import freelance.application.command.Command;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.billing.UpdateValidationStatusCommand;
import freelance.application.command.utils.AuthProvider;
import org.springframework.stereotype.Service;

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
