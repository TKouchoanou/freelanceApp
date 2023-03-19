package freelance.service.command.handler.company;

import freelance.domain.models.entity.Company;
import freelance.domain.models.entity.Rib;
import freelance.domain.models.objetValue.RibId;
import freelance.domain.repository.CompanyRepository;
import freelance.domain.repository.RibRepository;
import freelance.service.command.Command;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.company.CreateCompanyCommand;
import freelance.service.command.Command.Usecase;
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
