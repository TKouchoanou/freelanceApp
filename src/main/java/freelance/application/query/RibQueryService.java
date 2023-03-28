package freelance.application.query;

import freelance.domain.core.objetValue.EmployeeRole;
import freelance.application.query.annotation.RolesAllowed;
import freelance.application.query.model.Rib;
import freelance.application.query.query.SearchRibQuery;

import java.util.Optional;
import java.util.stream.Stream;

public interface RibQueryService {
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE},allowOwner = true)
    Stream<Rib> searchByIban(String iban);
    @RolesAllowed({EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE})
    Stream<Rib> searchByUserName(String userName);
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE})
    Stream<Rib> search(SearchRibQuery searchRibQuery);
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE})
    Optional<Rib> getById(Long id);
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE},allowOwner = true)
    Stream<Rib> getAll();
}
