package freelance.domain.models.entity;

import freelance.domain.models.objetValue.Profile;
import freelance.domain.models.objetValue.UserId;

import java.time.LocalDateTime;
import java.util.List;

public class User extends Auditable{
    UserId id;
    String firstName;
    String lastName;
    String email;
    String passWord;
    List<Profile> profiles;
    boolean isActive;
}
