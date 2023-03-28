package freelance.application.query;

import freelance.domain.core.objetValue.EmployeeRole;
import freelance.application.query.annotation.RolesAllowed;
import freelance.application.query.model.Employee;
import freelance.application.query.query.SearchEmployeeQuery;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface EmployeeQueryService {
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE})
    List<Employee> getAllEmployee();
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE},allowOwner = true)
    Optional<Employee> geEmployeeById(Long id);
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE})
    Stream<Employee> searchEmployee(SearchEmployeeQuery query);
}
