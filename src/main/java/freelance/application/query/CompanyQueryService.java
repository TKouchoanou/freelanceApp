package freelance.application.query;

import freelance.domain.core.objetValue.EmployeeRole;
import freelance.application.query.annotation.RolesAllowed;
import freelance.application.query.model.Company;


import java.util.Optional;
import java.util.stream.Stream;

public interface CompanyQueryService {
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE})
    Optional<Company> getById(Long id);
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE},allowOwner = true)
    Stream<Company> getAll();
}
