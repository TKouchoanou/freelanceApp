package freelance.service.query;

import freelance.domain.models.objetValue.EmployeeRole;
import freelance.service.query.annotation.RolesAllowed;
import freelance.service.query.model.Employee;
import freelance.service.query.model.EmployeeSummary;
import freelance.service.query.query.SearchEmployeeQuery;

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
