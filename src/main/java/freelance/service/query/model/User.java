package freelance.service.query.model;

import freelance.domain.models.objetValue.Email;
import freelance.domain.models.objetValue.Password;
import freelance.domain.models.objetValue.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Builder
@Getter
@Setter
public class User {
    Long id;
    String firstName;
    String lastName;
    String email;

    Set<String> profiles=new HashSet<>();
    boolean isActive;
}
