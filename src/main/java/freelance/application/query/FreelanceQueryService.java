package freelance.application.query;

import freelance.domain.core.objetValue.EmployeeRole;
import freelance.application.query.annotation.RolesAllowed;
import freelance.application.query.model.Freelance;
import freelance.application.query.model.FreelanceSummary;
import freelance.application.query.query.SearchFreelanceQuery;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface FreelanceQueryService {
    @RolesAllowed({EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE})
    List<FreelanceSummary> getAllFreelances();
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE},allowOwner = true)
    Optional<Freelance> getFreelanceById(Long id);
    @RolesAllowed(value = {EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE},allowOwner = true)
    Stream<FreelanceSummary> searchFreelanceByRib(Long ribId);
    @RolesAllowed({EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE})
    Stream<FreelanceSummary> searchFreelanceByCompany(Long companyId);
    @RolesAllowed({EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE})
    Stream<FreelanceSummary> searchFreelance(SearchFreelanceQuery query);
}
