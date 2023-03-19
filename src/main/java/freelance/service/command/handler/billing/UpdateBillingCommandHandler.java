package freelance.service.command.handler.billing;

import freelance.domain.models.entity.Billing;
import freelance.domain.models.entity.Company;
import freelance.domain.models.entity.Rib;
import freelance.domain.models.objetValue.BillingId;
import freelance.domain.models.objetValue.CompanyId;
import freelance.domain.models.objetValue.Period;
import freelance.domain.models.objetValue.RibId;
import freelance.domain.repository.BillingRepository;
import freelance.domain.repository.CompanyRepository;
import freelance.domain.repository.RibRepository;
import freelance.domain.repository.UserRepository;
import freelance.domain.security.Auth;
import freelance.service.command.Command;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.billing.UpdateBillingCommand;
import freelance.service.command.utils.AuthProvider;
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
