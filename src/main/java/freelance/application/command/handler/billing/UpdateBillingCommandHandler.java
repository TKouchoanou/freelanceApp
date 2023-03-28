package freelance.application.command.handler.billing;

import freelance.domain.core.entity.Billing;
import freelance.domain.core.entity.Company;
import freelance.domain.core.entity.Rib;
import freelance.domain.core.objetValue.BillingId;
import freelance.domain.core.objetValue.CompanyId;
import freelance.domain.core.objetValue.Period;
import freelance.domain.core.objetValue.RibId;
import freelance.domain.output.repository.BillingRepository;
import freelance.domain.output.repository.CompanyRepository;
import freelance.domain.output.repository.RibRepository;
import freelance.domain.output.repository.UserRepository;
import freelance.domain.common.security.Auth;
import freelance.application.command.Command;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.billing.UpdateBillingCommand;
import freelance.application.command.utils.AuthProvider;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UpdateBillingCommandHandler implements CommandHandler {
    BillingRepository billingRepository;
    UserRepository userRepository;
    CompanyRepository companyRepository;
    RibRepository ribRepository;
    AuthProvider authProvider;
    UpdateBillingCommandHandler(  BillingRepository billingRepository,
                                  UserRepository userRepository,
                                  CompanyRepository companyRepository,
                                  RibRepository ribRepository,
                                  AuthProvider authProvider){
        this.billingRepository=billingRepository;
        this.companyRepository=companyRepository;
        this.userRepository=userRepository;
        this.ribRepository=ribRepository;
        this.authProvider=authProvider;
    }

    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof UpdateBillingCommand cmd)){
            return;
        }
        Auth auth = authProvider.getCurrentAuth();
        Billing billing=billingRepository.getById(new BillingId(cmd.getBillingId()));
        Rib rib= ribRepository.getById(new RibId(cmd.getRibId()));
        Company company= Optional
                .ofNullable(cmd.getCompanyId()).map(CompanyId::new)
                .map(companyRepository::getById).orElse(null);
        Period period= new Period(cmd.getStartedDate(),cmd.getEndedDate());
        Money htAmount= Money.of(cmd.getAmountHT(), "EUR");
        Money ttcAmount= Money.of(cmd.getAmountTTC(),"EUR");
        billing.updateAmount(ttcAmount,htAmount,auth);

        billing.changePeriod(period,auth);
        if(company!=null){
            billing.changeCompany(company,auth);
        }
        if(!rib.getId().equals(billing.getRibId())){
            billing.changeRib(rib,auth);
        }
        billingRepository.save(billing);

    }
}
