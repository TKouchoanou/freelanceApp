package freelance.service.command.handler.company;

import freelance.domain.models.entity.Company;
import freelance.domain.models.entity.Freelance;
import freelance.domain.models.entity.Rib;
import freelance.domain.models.objetValue.CompanyId;
import freelance.domain.models.objetValue.RibId;
import freelance.domain.repository.CompanyRepository;
import freelance.domain.repository.FreelanceRepository;
import freelance.domain.repository.RibRepository;
import freelance.service.command.Command;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.company.UpdateCompanyCommand;
import freelance.service.command.utils.AuthProvider;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
@Service
public class UpdateCompanyCommandHandler  implements CommandHandler {
    CompanyRepository companyRepository;
    RibRepository ribRepository;

    AuthProvider authProvider;
    FreelanceRepository freelanceRepository;
    UpdateCompanyCommandHandler( CompanyRepository companyRepository, RibRepository ribRepository,
                                 AuthProvider authProvider,FreelanceRepository freelanceRepository){
        this.companyRepository=companyRepository; this.ribRepository=ribRepository;
        this.authProvider=authProvider; this.freelanceRepository=freelanceRepository;
    }
    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof UpdateCompanyCommand cmd)){
            return;
        }
        Company company=companyRepository.getById(new CompanyId(cmd.getCompanyId()));
        Rib rib = null;
        if(cmd.getRibId()!=null){
             rib=ribRepository.findById(new RibId(cmd.getRibId())).orElse(null);
        }
        Set<Freelance> freelances=freelanceRepository.findByCompany(company).collect(Collectors.toSet());
        company.changeRib(rib,freelances,authProvider.getCurrentAuth());
        freelanceRepository.saveAll(freelances);
        companyRepository.save(company);
    }
}
