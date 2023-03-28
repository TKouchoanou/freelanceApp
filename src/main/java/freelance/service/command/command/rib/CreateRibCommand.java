package freelance.service.command.command.rib;

import freelance.service.command.Command.Usecase;
import freelance.service.command.CommandException;
import freelance.service.command.handler.rib.CreateRibCommandHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Usecase(handlers = CreateRibCommandHandler.class)
public class CreateRibCommand  extends AbstractRibCommand {
    Long ribId;

    @Override
    public void validateStateBeforeHandling() {
      if(iban==null || iban.isBlank()){
         throw new CommandException("you did not provide required iban ");
      }
      if(ribId!=null){
          throw new CommandException("rib id must be null ");
      }
    }
    public void setCreateRibId(Long ribId) {
        this.ribId = ribId;
    }
    public Long getCreateRibId() {
        return ribId;
    }
    @Override
    public void validateStateAfterHandling() {
        if(this.ribId ==null)
            throw new CommandException(" value id can be null after handling "+this.getClass().getName());
    }




}
