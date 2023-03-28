package freelance.service.command.command.user;


import freelance.service.command.Command;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractUserCommand implements Command {
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    boolean isActive;
}
