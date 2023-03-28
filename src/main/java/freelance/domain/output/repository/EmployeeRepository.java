package freelance.domain.output.repository;

import freelance.domain.core.entity.Employee;
import freelance.domain.core.objetValue.EmployeeId;
import freelance.domain.core.objetValue.UserId;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, EmployeeId>{
    Optional<Employee> findByUserId(UserId userId) ;
    default String getEntityName() {
        return "Employee";
    }
}
