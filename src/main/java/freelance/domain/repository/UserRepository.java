package freelance.domain.repository;

import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.Email;
import freelance.domain.models.objetValue.UserId;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,UserId>{
    public Optional<User> findByEmail(Email email);
    default String getEntityName() {
        return "User";
    }
}
