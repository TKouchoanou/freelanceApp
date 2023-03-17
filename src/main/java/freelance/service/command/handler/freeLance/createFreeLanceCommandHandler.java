package freelance.service.command.handler.freeLance;
import freelance.service.command.Command.Usecase;
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
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class createFreeLanceCommandHandler implements CommandHandler {
    UserRepository userRepository;
    FreelanceRepository freelanceRepository;
    CompanyRepository companyRepository;
    RibRepository ribRepository;
    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof CreateFreeLanceCommand cmd)){
            return;
        }
        UserId userId=new UserId(cmd.getUserId());
        Optional<Freelance> optionalFreelance= freelanceRepository.findByUserId(userId);
        optionalFreelance.ifPresent(freelance -> {
            throw new CommandException("freelance profile already exist with this user, it's id is "+freelance.getId().value());
        });

        User user=userRepository.getById(userId);
        Freelance freelance;
        if(cmd.hasCompanyId()){
            Company company=companyRepository.getById(new CompanyId(cmd.getCompanyId()));
            freelance=freelanceRepository.save(new Freelance(user,company));
            companyRepository.save(company);

        }else {
            Rib rib=ribRepository.getById(new RibId(cmd.getRibId()));
            freelance=freelanceRepository.save(new Freelance(user,rib));
            ribRepository.save(rib);
        }

        cmd.setFreeLanceId(freelance.getId().value());

    }
}
