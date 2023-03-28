package freelance.application.query.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    Set<String> profiles;
    boolean isActive;

}
