package freelance.application.query.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Builder
@Getter
@Setter
public class User {
    Long id;
    String firstName;
    String lastName;
    String email;

    Set<String> profiles;
    boolean isActive;
}
