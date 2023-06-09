package freelance.application.command.handler.billing;

import freelance.application.command.Command;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.billing.CreateBillingCommand;
import freelance.application.command.utils.AuthProvider;
import freelance.domain.core.entity.Billing;
import freelance.domain.core.entity.Company;
import freelance.domain.core.entity.Rib;
import freelance.domain.core.entity.User;
import freelance.domain.core.objetValue.*;
import freelance.domain.output.files.FileStorageService;
import freelance.domain.output.repository.BillingRepository;
import freelance.domain.output.repository.CompanyRepository;
import freelance.domain.output.repository.RibRepository;
import freelance.domain.output.repository.UserRepository;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CreateBillingCommandHandler implements CommandHandler {
    BillingRepository billingRepository;
    UserRepository userRepository;
    CompanyRepository companyRepository;
    RibRepository ribRepository;
    AuthProvider authProvider;
    FileStorageService fileStorageService;
    CreateBillingCommandHandler(  BillingRepository billingRepository,
    UserRepository userRepository,
    CompanyRepository companyRepository,
    FileStorageService fileStorageService,
    RibRepository ribRepository,
    AuthProvider authProvider){
        this.billingRepository=billingRepository;
        this.companyRepository=companyRepository;
        this.userRepository=userRepository;
        this.ribRepository=ribRepository;
        this.authProvider=authProvider;
        this.fileStorageService=fileStorageService;
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
        //store billing file
        File file=  fileStorageService.store(new File(null, cmd.getFile(),"billing"));
        BillingFile billingFile= new BillingFile(file.id(),file.context(),cmd.getFileName());
        
        //save the billing
        Billing billing = billingRepository.save(new Billing(user,rib,company,period,ttcAmount,htAmount,billingFile));
      //valorise command id and status
        cmd.setBillingId(billing.getId().id());
        cmd.setValidationStatus(billing.getValidationStatus().name());
        cmd.setPaymentStatus(billing.getPaymentStatus().name());
    }
}
