package freelance.service.command.command.user;


import freelance.domain.models.objetValue.Profile;
import freelance.service.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class AbstractUserCommand implements Command {
    Long id;
    String firstName;
    String lastName;
    String email;

    boolean isActive;
}
