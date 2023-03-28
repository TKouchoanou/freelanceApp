package freelance.application.command.handler.company;

import freelance.domain.core.entity.Company;
import freelance.domain.core.entity.Rib;
import freelance.domain.core.objetValue.RibId;
import freelance.domain.output.repository.CompanyRepository;
import freelance.domain.output.repository.RibRepository;
import freelance.application.command.Command;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.company.CreateCompanyCommand;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyCommandHandler implements CommandHandler {
    CompanyRepository companyRepository;
    RibRepository ribRepository;
    CreateCompanyCommandHandler(CompanyRepository companyRepository, RibRepository ribRepository){
        this.companyRepository=companyRepository;
        this.ribRepository=ribRepository;
    }

    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof CreateCompanyCommand cmd)){
            return;
        }
        Rib rib= ribRepository.getById(new RibId(cmd.getRibId()));
        Company company= companyRepository.save(new Company(rib,cmd.getName()));
        cmd.setCompanyId(company.getId().id());
    }
}
