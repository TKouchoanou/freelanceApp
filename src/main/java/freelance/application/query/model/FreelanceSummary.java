package freelance.application.query.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Builder
@Setter
@Getter
public class FreelanceSummary {
    Long id;
    Long userId;
    String firstName;
    String lastName;
    String email;
    String companyName;
    Long ribId;
    Set<String> profiles;
    boolean isActive;
}
