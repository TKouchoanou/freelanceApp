package freelance.application.command.handler.rib;

import freelance.domain.core.entity.Rib;
import freelance.domain.output.repository.RibRepository;
import freelance.application.command.Command;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.rib.CreateRibCommand;
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
