package freelance.application.command.handler.company;

import freelance.domain.core.entity.Company;
import freelance.domain.core.entity.Freelance;
import freelance.domain.core.entity.Rib;
import freelance.domain.core.objetValue.CompanyId;
import freelance.domain.core.objetValue.RibId;
import freelance.domain.output.repository.CompanyRepository;
import freelance.domain.output.repository.FreelanceRepository;
import freelance.domain.output.repository.RibRepository;
import freelance.application.command.Command;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.company.UpdateCompanyCommand;
import freelance.application.command.utils.AuthProvider;
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
             rib=ribRepository.getById(new RibId(cmd.getRibId()));
        }
        Set<Freelance> freelances=freelanceRepository.findByCompany(company.getId()).collect(Collectors.toSet());
        company.changeRib(rib,freelances,authProvider.getCurrentAuth());
        freelanceRepository.saveAll(freelances);
        companyRepository.save(company);
    }
}
