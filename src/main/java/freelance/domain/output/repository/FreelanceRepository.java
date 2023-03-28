package freelance.domain.output.repository;

import freelance.domain.core.entity.Freelance;
import freelance.domain.core.objetValue.CompanyId;
import freelance.domain.core.objetValue.FreelanceId;
import freelance.domain.core.objetValue.RibId;
import freelance.domain.core.objetValue.UserId;

import java.util.Optional;
import java.util.stream.Stream;

public interface FreelanceRepository extends CrudRepository<Freelance, FreelanceId>{

    Optional<Freelance> findByUserId(UserId userId) ;
    Stream<Freelance> findByCompany(CompanyId companyId) ;
    Stream<Freelance> findByRibId(RibId ribId);
}
