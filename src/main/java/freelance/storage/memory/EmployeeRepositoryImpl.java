package freelance.storage.memory;

import freelance.domain.models.entity.Employee;
import freelance.domain.models.objetValue.EmployeeId;
import freelance.domain.models.objetValue.FreelanceId;
import freelance.domain.models.objetValue.UserId;
import freelance.domain.repository.EmployeeRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
@Repository
@SuppressWarnings("unchecked")
public class EmployeeRepositoryImpl implements EmployeeRepository {
    Map<EmployeeId, Employee> stores= new HashMap<>();
    @Override
    public Stream<Employee> findAll() {
        return stores.values().stream();
    }

    @Override
    public Stream<Employee> findBy(Employee example) {
        return Stream.empty();
    }

    @Override
    public Optional<Employee> findById(EmployeeId employeeId) {
       return stores.values().stream()
                .filter(employ->employeeId.equals(employ.getId()))
                .findAny();
    }

    @Override
    public <S extends Employee> S save(S employee) {
        Employee saved;
        if(employee.getId()==null){
            EmployeeId id=nextId();
            saved=new Employee(id,employee.getUserId(),employee.getEmployeeRoles(),employee.getCreatedDate(),employee.getUpdatedDate());
        }else {
            saved= new Employee(employee.getId(),employee.getUserId(),employee.getEmployeeRoles(),employee.getCreatedDate(),employee.getUpdatedDate());
        }
        stores.put(saved.getId(),saved);
        return (S) saved;
    }

    private EmployeeId nextId() {
        return stores
                .keySet()
                .stream()
                .map(EmployeeId::value)
                .max(Long::compareTo)
                .map(last->new EmployeeId(last+1))
                .orElse(new EmployeeId(1L));

    }

    @Override
    public void deleteById(EmployeeId employeeId) {

    }

    @Override
    public <S extends Employee> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Employee> findByUserId(UserId userId) {
        return stores.values().stream()
                .filter(e->userId.equals(e.getUserId()))
                .findAny();
    }
}
