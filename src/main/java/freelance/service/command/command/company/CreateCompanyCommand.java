package freelance.service.command.command.company;

import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.Command.Usecase;
import freelance.service.command.handler.company.CreateCompanyCommandHandler;
import freelance.service.utils.TypeUtils;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Usecase(handlers = {CreateCompanyCommandHandler.class})
public class CreateCompanyCommand implements Command {
    String name;
    Long ribId;
    Long companyId;
    @Override
    public void validateStateBeforeHandling() {
        if(!TypeUtils.hasValue(name)){
         throw new CommandException("empty name is not allowed");
        }
        if(!TypeUtils.hasValue(ribId)){
            throw new CommandException("empty ribId is not allowed");
        }

    }

    @Override
    public void validateStateAfterHandling() {
        if(!TypeUtils.hasValue(companyId)){
            throw new CommandException("empty companyId");
        }
    }
}
