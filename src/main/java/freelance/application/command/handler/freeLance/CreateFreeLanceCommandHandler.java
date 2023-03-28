package freelance.application.command.handler.freeLance;
import freelance.domain.core.entity.Company;
import freelance.domain.core.entity.Freelance;
import freelance.domain.core.entity.Rib;
import freelance.domain.core.entity.User;
import freelance.domain.core.objetValue.CompanyId;
import freelance.domain.core.objetValue.RibId;
import freelance.domain.core.objetValue.UserId;
import freelance.domain.output.repository.CompanyRepository;
import freelance.domain.output.repository.FreelanceRepository;
import freelance.domain.output.repository.RibRepository;
import freelance.domain.output.repository.UserRepository;
import freelance.application.command.Command;
import freelance.application.command.CommandException;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.freeLance.CreateFreeLanceCommand;
import freelance.application.command.utils.AuthProvider;
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
