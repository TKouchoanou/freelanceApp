package freelance.service.command.handler.billing;

import freelance.domain.models.entity.Billing;
import freelance.domain.models.entity.Company;
import freelance.domain.models.entity.Rib;
import freelance.domain.models.objetValue.*;
import freelance.domain.repository.BillingRepository;
import freelance.domain.repository.CompanyRepository;
import freelance.domain.repository.RibRepository;
import freelance.domain.repository.UserRepository;
import freelance.service.command.Command;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.billing.UpdateBillingCommand;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import freelance.service.command.Command.Usecase;

import java.util.Optional;
@Service
public class UpdateBillingCommandHandler implements CommandHandler {
    BillingRepository billingRepository;
    UserRepository userRepository;
    CompanyRepository companyRepository;
    RibRepository ribRepository;

    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof UpdateBillingCommand cmd)){
            return;
        }
        Billing billing=billingRepository.getById(new BillingId(cmd.getBillingId()));
        Rib rib= ribRepository.getById(new RibId(cmd.getRibId()));
        Company company= Optional
                .ofNullable(cmd.getCompanyId()).map(CompanyId::new)
                .map(companyRepository::getById).orElse(null);
        Period period= new Period(cmd.getStartedDate(),cmd.getEndedDate());
        Money htAmount= Money.of(cmd.getAmountHT(), "EUR");
        Money ttcAmount= Money.of(cmd.getAmountTTC(),"EUR");

    }
}
