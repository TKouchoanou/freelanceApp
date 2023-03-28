package freelance.service.command.command.rib;

import freelance.service.command.Command.Usecase;
import freelance.service.command.CommandException;
import freelance.service.command.handler.rib.UpdateRibCommandHandler;
import freelance.service.command.utils.validation.NotEmptyNumber;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Usecase(handlers = {UpdateRibCommandHandler.class})
public class UpdateRibCommand extends AbstractRibCommand{
    @NotEmptyNumber
    Long ribId;

    @Override
    public void validateStateBeforeHandling() {
        if(iban==null || iban.isBlank()){
            throw new CommandException("you did not provide required iban ");
        }
        if(ribId==null){
            throw new CommandException("cannot update rib with null id");
        }
    }
    public void setUpdatedRibId(Long ribId) {
        this.ribId = ribId;
    }
    public Long getRibId() {
        return ribId;
    }


    @Override
    public void validateStateAfterHandling() {
        if(this.ribId ==null)
            throw new CommandException(" value id can be null after handling "+this.getClass().getName());
    }
}
