package freelance.domain.repository;

import freelance.domain.models.entity.Company;
import freelance.domain.models.entity.Freelance;
import freelance.domain.models.objetValue.FreelanceId;
import freelance.domain.models.objetValue.UserId;

import java.util.Optional;
import java.util.stream.Stream;

public interface FreelanceRepository extends CrudRepository<Freelance, FreelanceId>{

    Optional<Freelance> findByUserId(UserId userId) ;
    Stream<Freelance> findByCompany(Company company) ;
}
