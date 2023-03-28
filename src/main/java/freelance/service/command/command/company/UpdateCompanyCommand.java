package freelance.service.command.command.company;

import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.handler.company.UpdateCompanyCommandHandler;
import freelance.service.command.utils.validation.NotEmptyNumber;
import freelance.service.utils.TypeUtils;
import jakarta.validation.constraints.NotNull;
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
