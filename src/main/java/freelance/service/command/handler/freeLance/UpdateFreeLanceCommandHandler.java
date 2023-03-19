package freelance.service.command.handler.freeLance;
import freelance.domain.repository.UserRepository;
import freelance.service.command.Command.Usecase;
import freelance.domain.models.entity.Company;
import freelance.domain.models.entity.Freelance;
import freelance.domain.models.entity.Rib;
import freelance.domain.models.objetValue.CompanyId;
import freelance.domain.models.objetValue.FreelanceId;
import freelance.domain.models.objetValue.RibId;
import freelance.domain.repository.CompanyRepository;
import freelance.domain.repository.FreelanceRepository;
import freelance.domain.repository.RibRepository;
import freelance.domain.security.Auth;
import freelance.service.command.Command;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.freeLance.UpdateFreeLanceCommand;
import freelance.service.command.utils.AuthProvider;
import freelance.service.utils.TypeUtils;
import org.springframework.stereotype.Service;

@Service
public class UpdateFreeLanceCommandHandler implements CommandHandler {
    FreelanceRepository freelanceRepository;
    CompanyRepository companyRepository;
    RibRepository ribRepository;
    AuthProvider authProvider;
    UpdateFreeLanceCommandHandler(FreelanceRepository freelanceRepository,
                                  CompanyRepository companyRepository, RibRepository ribRepository,AuthProvider authProvider){
        this.ribRepository=ribRepository;
        this.companyRepository=companyRepository;
        this.freelanceRepository=freelanceRepository;
        this.authProvider=authProvider;
    }

    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof UpdateFreeLanceCommand cmd)){
            return;
        }
        Auth auth = authProvider.getCurrentAuth();
        Company company,oldCompany; Rib rib,oldRib;
        Freelance freelance = freelanceRepository.getById(new FreelanceId(cmd.getFreeLanceId()));
        if(TypeUtils.hasValue(cmd.getCompanyId())){
             company= companyRepository.getById(new CompanyId(cmd.getCompanyId()));
             oldCompany= companyRepository.getById(new CompanyId(cmd.getCompanyId()));
             if(!oldCompany.equals(company)){
                 freelance.changeCompany(oldCompany, company,auth);
             }
        }

        if(TypeUtils.hasValue(cmd.getRibId())){
             oldRib=ribRepository.getById(new RibId(cmd.getRibId()));
             if(!oldRib.getId().equals(new RibId(cmd.getRibId()))){
                 rib=ribRepository.getById(new RibId(cmd.getRibId()));
                 freelance.changeRib(rib,auth);
             }
        }



    }
}
