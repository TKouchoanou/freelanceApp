package freelance.service.query.model;



import freelance.domain.models.objetValue.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Setter
@Getter
@Builder
public class Employee {
    Long id;
    Long userid;
    Set<String> roles;
    String firstName;
    String lastName;
    String email;
    Set<String> profiles=new HashSet<>();
    boolean isActive;

}
