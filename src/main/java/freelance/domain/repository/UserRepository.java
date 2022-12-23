package freelance.domain.repository;

import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.UserId;
import freelance.service.command.CommandException;

public interface UserRepository extends CrudRepository<User,UserId>{

}
