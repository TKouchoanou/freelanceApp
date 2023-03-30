package freelance.application.query;


import freelance.application.query.model.File;
import freelance.domain.core.objetValue.EmployeeRole;
import freelance.application.query.annotation.RolesAllowed;
import freelance.application.query.model.Billing;
import freelance.application.query.model.BillingSummary;
import freelance.application.query.query.SearchBillingQuery;

import java.io.BufferedInputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface BillingQueryService {
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE,EmployeeRole.FINANCE})
    List<Billing> getAllBillings();
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE,EmployeeRole.FINANCE})
    List<BillingSummary> getAllBillingsSummary();
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE,EmployeeRole.FINANCE},allowOwner = true)
    Optional<Billing> getBillingById(Long id);
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE,EmployeeRole.FINANCE})
    Stream<Billing> searchBillingByRib(Long ribId);
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE,EmployeeRole.FINANCE})
    Stream<Billing> searchBillingByCompany(Long companyId);
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE,EmployeeRole.FINANCE})
    Stream<Billing> searchBilling(SearchBillingQuery query);
    File loadBillingFile(Long id);
}
