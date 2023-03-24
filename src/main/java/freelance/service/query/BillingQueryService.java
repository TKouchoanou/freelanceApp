package freelance.service.query;


import freelance.domain.models.objetValue.EmployeeRole;
import freelance.service.query.annotation.RolesAllowed;
import freelance.service.query.model.Billing;
import freelance.service.query.query.SearchBillingQuery;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface BillingQueryService {
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE,EmployeeRole.FINANCE})
    List<Billing> getAllBillings();
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE,EmployeeRole.FINANCE},allowOwner = true)
    Optional<Billing> getBillingById(Long id);
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE,EmployeeRole.FINANCE})
    Stream<Billing> searchBillingByRib(Long ribId);
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE,EmployeeRole.FINANCE})
    Stream<Billing> searchBillingByCompany(Long companyId);
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE,EmployeeRole.FINANCE})
    Stream<Billing> searchBilling(SearchBillingQuery query);
}
