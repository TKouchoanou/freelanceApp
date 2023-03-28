package freelance.service.command.handler.billing;

import freelance.domain.models.entity.Billing;
import freelance.domain.models.entity.Company;
import freelance.domain.models.entity.Rib;
import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.*;
import freelance.domain.repository.BillingRepository;
import freelance.domain.repository.CompanyRepository;
import freelance.domain.repository.RibRepository;
import freelance.domain.repository.UserRepository;
import freelance.service.command.Command;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.billing.CreateBillingCommand;
import freelance.service.command.utils.AuthProvider;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class CreateBillingCommandHandler implements CommandHandler {
    BillingRepository billingRepository;
    UserRepository userRepository;
    CompanyRepository companyRepository;
    RibRepository ribRepository;
    AuthProvider authProvider;
    CreateBillingCommandHandler(  BillingRepository billingRepository,
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
        if(!(command instanceof CreateBillingCommand cmd)){
            return;
        }
        User user=userRepository.getById(new UserId(cmd.getUserId()));
        Rib rib= ribRepository.getById(new RibId(cmd.getRibId()));
        Company company= Optional
                .ofNullable(cmd.getCompanyId()).map(CompanyId::new)
                .map(companyRepository::getById).orElse(null);
        Period period= new Period(cmd.getStartedDate(),cmd.getEndedDate());
        Money htAmount= Money.of(cmd.getAmountHT(), "EUR");
        Money ttcAmount= Money.of(cmd.getAmountTTC(),"EUR");
        BillingFile billingFile= new BillingFile(cmd.getFile(), Optional.ofNullable(cmd.getFileName()).orElse("billing_"+user.getFirstName()+"_"+user.getId().id()+ LocalDateTime.now()));
        Billing billing = billingRepository.save(new Billing(user,rib,company,period,ttcAmount,htAmount,billingFile));
        cmd.setBillingId(billing.getId().id());
        cmd.setValidationStatus(billing.getValidationStatus().name());
        cmd.setPaymentStatus(billing.getPaymentStatus().name());
    }
}
