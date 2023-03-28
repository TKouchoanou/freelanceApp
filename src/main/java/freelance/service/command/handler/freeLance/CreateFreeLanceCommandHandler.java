package freelance.service.command.handler.freeLance;
import freelance.domain.models.entity.Company;
import freelance.domain.models.entity.Freelance;
import freelance.domain.models.entity.Rib;
import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.CompanyId;
import freelance.domain.models.objetValue.RibId;
import freelance.domain.models.objetValue.UserId;
import freelance.domain.repository.CompanyRepository;
import freelance.domain.repository.FreelanceRepository;
import freelance.domain.repository.RibRepository;
import freelance.domain.repository.UserRepository;
import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.freeLance.CreateFreeLanceCommand;
import freelance.service.command.utils.AuthProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CreateFreeLanceCommandHandler implements CommandHandler {
    UserRepository userRepository;
    FreelanceRepository freelanceRepository;
    CompanyRepository companyRepository;
    RibRepository ribRepository;
    AuthProvider authProvider;
    CreateFreeLanceCommandHandler( UserRepository userRepository, FreelanceRepository freelanceRepository,
    CompanyRepository companyRepository, RibRepository ribRepository,AuthProvider authProvider){
        this.ribRepository=ribRepository;
        this.companyRepository=companyRepository;
        this.freelanceRepository=freelanceRepository;
        this.userRepository=userRepository;
        this.authProvider=authProvider;
    }
  @Override
    public void handle(Command command, HandlingContext handlingContext) {
        // Vérifier si la commande est de type CreateFreeLanceCommand
        if (!(command instanceof CreateFreeLanceCommand cmd)) {
            return;
        }

        // Obtenir l'ID de l'utilisateur de la commande
      UserId userId = new UserId(cmd.getUserId());

        // Vérifier si un profil de freelance existe déjà pour cet utilisateur
        Optional<Freelance> optionalFreelance = freelanceRepository.findByUserId(userId);
        optionalFreelance.ifPresent(freelance -> {
            throw new CommandException("A freelance profile already exists with this user, its ID is " + freelance.getId().value());
        });

        // Obtenir l'utilisateur correspondant à l'ID de l'utilisateur de la commande
        User user = userRepository.getById(userId);

        // Créer un profil de freelance en fonction de la présence d'une entreprise et/ou d'un RIB dans la commande
        Freelance freelance;
        if (cmd.hasCompanyId() && cmd.hasRibId()) {
            CompanyId companyId = new CompanyId(cmd.getCompanyId());
            Company company = companyRepository.getById(companyId);
            RibId ribId = new RibId(cmd.getRibId());
            Rib rib = ribRepository.getById(ribId);
            freelance = freelanceRepository.save(new Freelance(user, rib, company));
            companyRepository.save(company);
            ribRepository.save(rib);
        } else if (cmd.hasCompanyId()) {
            CompanyId companyId = new CompanyId(cmd.getCompanyId());
            Company company = companyRepository.getById(companyId);
            freelance = freelanceRepository.save(new Freelance(user, company));
            company.addFreelance(freelance,this.authProvider.getCurrentAuth());
            companyRepository.save(company);
        } else if (cmd.hasRibId()) {
            RibId ribId = new RibId(cmd.getRibId());
            Rib rib = ribRepository.getById(ribId);
            freelance = freelanceRepository.save(new Freelance(user, rib));
            ribRepository.save(rib);
            ribRepository.save(rib);
        } else {
            throw new CommandException("Unsupported create freelance command ");
        }

        // Définir l'ID du profil de freelance créé dans la commande
        cmd.setId(freelance.getId().value());
    }
}
