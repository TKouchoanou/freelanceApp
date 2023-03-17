package freelance.service.command.handler.rib;
import freelance.service.command.Command.Usecase;
import freelance.domain.models.entity.Rib;
import freelance.domain.repository.RibRepository;
import freelance.service.command.Command;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.rib.CreateRibCommand;
import org.springframework.stereotype.Service;

@Service
public class CreateRibCommandHandler implements CommandHandler {
    RibRepository ribRepository;
    CreateRibCommandHandler(RibRepository ribRepository){
        this.ribRepository=ribRepository;
    }
    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof CreateRibCommand cmd)){
            return;
        }
       Rib rib= ribRepository.save(new Rib(cmd.getUsername(),cmd.getIban(),cmd.getBic(),cmd.getCleRib()));
        cmd.setCreateRibId(rib.getId().value());
    }
}
