package freelance.domain.repository;

import freelance.domain.models.entity.Employee;
import freelance.domain.models.entity.Freelance;
import freelance.domain.models.objetValue.EmployeeId;
import freelance.domain.models.objetValue.UserId;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, EmployeeId>{
    Optional<Employee> findByUserId(UserId userId) ;
}
