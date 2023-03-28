package freelance.application.command.command.company;

import freelance.application.command.Command;
import freelance.application.command.CommandException;
import freelance.application.command.Command.Usecase;
import freelance.application.command.handler.company.CreateCompanyCommandHandler;
import freelance.application.command.utils.validation.NotEmptyNumber;
import freelance.application.utils.TypeUtils;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Usecase(handlers = {CreateCompanyCommandHandler.class})
public class CreateCompanyCommand implements Command {
    String name;
    @NotEmptyNumber
    Long ribId;
    @NotEmptyNumber
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
