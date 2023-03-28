package freelance.application.command.command.company;

import freelance.application.command.Command;
import freelance.application.command.CommandException;
import freelance.application.command.handler.company.UpdateCompanyCommandHandler;
import freelance.application.command.utils.validation.NotEmptyNumber;
import freelance.application.utils.TypeUtils;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Command.Usecase(handlers = {UpdateCompanyCommandHandler.class})
public class UpdateCompanyCommand implements Command {
    String name;
    @NotEmptyNumber
    Long ribId;
    @NotEmptyNumber
    Long companyId;
    @Override
    public void validateStateBeforeHandling() {
        if(!TypeUtils.hasValue(companyId)){
            throw new CommandException(" cannot update company with empty companyId");
        }
        if(!TypeUtils.hasValue(name)){
            throw new CommandException("empty name is not allowed for update");
        }
        if(!TypeUtils.hasValue(ribId)){
            throw new CommandException("empty ribId is not allowed for update");
        }
    }

    @Override
    public void validateStateAfterHandling() {

    }
}
