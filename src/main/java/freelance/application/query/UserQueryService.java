package freelance.application.query;

import freelance.domain.core.objetValue.EmployeeRole;
import freelance.application.query.annotation.RolesAllowed;
import freelance.application.query.model.User;
import freelance.application.query.query.SearchUserQuery;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface UserQueryService {
    @RolesAllowed({EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE})
    List<User> getAllUsers();
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE},allowOwner = true)
    Optional<User> geUserById(Long id);
    @RolesAllowed({EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE})
    Stream<User> searchUser(SearchUserQuery query);
}
