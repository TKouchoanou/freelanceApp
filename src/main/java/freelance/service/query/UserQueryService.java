package freelance.service.query;

import freelance.domain.models.objetValue.EmployeeRole;
import freelance.service.query.annotation.RolesAllowed;
import freelance.service.query.model.User;
import freelance.service.query.query.SearchUserQuery;

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
