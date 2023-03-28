package freelance.application.command.handler.rib;

import freelance.domain.core.entity.Billing;
import freelance.domain.core.entity.Rib;
import freelance.domain.core.objetValue.RibId;
import freelance.domain.output.repository.BillingRepository;
import freelance.domain.output.repository.RibRepository;
import freelance.domain.common.security.Auth;
import freelance.application.command.Command;
import freelance.application.command.CommandException;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.rib.UpdateRibCommand;
import freelance.application.command.utils.AuthProvider;
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
