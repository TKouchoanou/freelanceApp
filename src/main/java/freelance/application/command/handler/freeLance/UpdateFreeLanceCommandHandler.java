package freelance.application.command.handler.freeLance;
import freelance.domain.core.entity.Company;
import freelance.domain.core.entity.Freelance;
import freelance.domain.core.entity.Rib;
import freelance.domain.core.objetValue.CompanyId;
import freelance.domain.core.objetValue.FreelanceId;
import freelance.domain.core.objetValue.RibId;
import freelance.domain.output.repository.CompanyRepository;
import freelance.domain.output.repository.FreelanceRepository;
import freelance.domain.output.repository.RibRepository;
import freelance.domain.common.security.Auth;
import freelance.application.command.Command;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.freeLance.UpdateFreeLanceCommand;
import freelance.application.command.utils.AuthProvider;
import freelance.application.utils.TypeUtils;
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
        Freelance freelance = freelanceRepository.getById(new FreelanceId(cmd.getId()));
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
