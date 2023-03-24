package freelance.service.query;

import freelance.domain.models.objetValue.EmployeeRole;
import freelance.service.query.annotation.RolesAllowed;
import freelance.service.query.model.Freelance;
import freelance.service.query.model.FreelanceSummary;
import freelance.service.query.query.SearchFreelanceQuery;

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
