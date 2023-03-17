package freelance.service.command.command.rib;

import freelance.service.command.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractRibCommand implements Command {
    Long ribId;
    String username;
    String iban;
    String bic;
    String cleRib;
    public String getBic() {
        return bic;
    }

    public String getIban() {
        return iban;
    }

    public String getUsername() {
        return username;
    }




    public String getCleRib() {
        return cleRib;
    }

    public Long getRibId() {
        return ribId;
    }
}
