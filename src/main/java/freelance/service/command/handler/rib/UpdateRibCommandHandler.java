package freelance.service.command.handler.rib;

import freelance.domain.models.entity.Billing;
import freelance.domain.models.entity.Rib;
import freelance.domain.models.objetValue.RibId;
import freelance.domain.repository.BillingRepository;
import freelance.domain.repository.RibRepository;
import freelance.domain.security.Auth;
import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.rib.UpdateRibCommand;
import freelance.service.command.utils.AuthProvider;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UpdateRibCommandHandler implements CommandHandler {
    RibRepository ribRepository;
    AuthProvider authProvider;
    BillingRepository billingRepository;

    UpdateRibCommandHandler( RibRepository ribRepository, AuthProvider authProvider,
                             BillingRepository billingRepository){
        this.ribRepository=ribRepository;
        this.authProvider=authProvider;
        this.billingRepository=billingRepository;
    }
    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof UpdateRibCommand cmd)){
            return;
        }
        Auth auth= authProvider.getCurrentAuth();
        Rib rib=  getRib(cmd);
        Set<Billing> billings= getBillings(rib);
        rib.update(cmd.getUsername(),cmd.getBic(),cmd.getCleRib(),auth);
        rib.update(cmd.getIban(),billings,auth);
        Rib result= ribRepository.save(rib);
        cmd.setUpdatedRibId(result.getId().value());
    }

    private Rib getRib(UpdateRibCommand command){
        RibId ribId=new RibId(command.getRibId());
      return  ribRepository.findById(ribId)
              .orElseThrow(()-> new CommandException("rib with id "+ribId+" not found "));
    }

    public Set<Billing> getBillings(Rib rib){
        return billingRepository.findAll(rib.getBillingIds()).collect(Collectors.toSet());
    }
}
