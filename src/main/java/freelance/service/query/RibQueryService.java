package freelance.service.query;

import freelance.domain.models.objetValue.EmployeeRole;
import freelance.service.query.annotation.RolesAllowed;
import freelance.service.query.model.Rib;
import freelance.service.query.query.SearchRibQuery;

import java.util.Optional;
import java.util.stream.Stream;

public interface RibQueryService {
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE},allowOwner = true)
    Stream<Rib> searchByIban(String iban);
    @RolesAllowed({EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE})
    Stream<Rib> searchByUserName(String userName);
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE})
    Stream<Rib> search(SearchRibQuery searchRibQuery);
}
