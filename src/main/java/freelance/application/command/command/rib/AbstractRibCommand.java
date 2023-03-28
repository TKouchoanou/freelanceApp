package freelance.application.command.command.rib;

import freelance.application.command.Command;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class AbstractRibCommand implements Command {
    String username;
    @Pattern(regexp = "FR\\d{12}[A-Z0-9]{11}\\d{2}")
    String iban;
    @Pattern(regexp = "\\b[A-Z]{8}(?:[A-Z]{3})?\\b")
    String bic;
    @Pattern(regexp = "^\\d{2}$")
    String cleRib;
}
