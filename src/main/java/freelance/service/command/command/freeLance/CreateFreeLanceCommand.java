package freelance.service.command.command.freeLance;

import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.utils.TypeUtils;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateFreeLanceCommand implements Command {
    Long ribId;
    Long userId;
    Long freeLanceId;
    Long companyId;
    @Override
    public void validateStateBeforeHandling() {
       if(!TypeUtils.hasValue(companyId) && TypeUtils.hasValue(ribId)){
           throw new CommandException("require ribId or companyId to create freelance");
       }
    }
       public boolean hasCompanyId(){
        return TypeUtils.hasValue(companyId);
         }
    @Override
    public void validateStateAfterHandling() {
       if(TypeUtils.hasValue(freeLanceId)){
           throw new CommandException("command handling failure because freelance id is not provided");
       }
    }
}
