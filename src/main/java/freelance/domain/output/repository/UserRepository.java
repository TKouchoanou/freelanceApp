package freelance.domain.output.repository;

import freelance.domain.core.entity.User;
import freelance.domain.core.objetValue.Email;
import freelance.domain.core.objetValue.UserId;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,UserId>{
    public Optional<User> findByEmail(Email email);
    default String getEntityName() {
        return "User";
    }
}
