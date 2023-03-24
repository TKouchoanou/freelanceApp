package freelance.storage.memory.query;

import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.EmployeeId;
import freelance.domain.repository.EmployeeRepository;
import freelance.domain.repository.UserRepository;
import freelance.service.query.EmployeeQueryService;
import freelance.service.query.model.Employee;
import freelance.service.query.query.SearchEmployeeQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
@Service
public class EmployeeQueryServiceImpl implements EmployeeQueryService {
    EmployeeRepository employeeRepository;
    UserRepository userRepository;
    EmployeeQueryServiceImpl(EmployeeRepository employeeRepository, UserRepository userRepository){
        this.employeeRepository=employeeRepository;
        this.userRepository=userRepository;
    }
    @Override
    public List<Employee> getAllEmployee() {
        return this.employeeRepository.findAll()
                .map(this::toQuery)
                .toList();
    }

    @Override
    public Optional<Employee> geEmployeeById(Long id) {
        return employeeRepository.findById(new EmployeeId(id))
                .map(this::toQuery);
    }

    @Override
    public Stream<Employee> searchEmployee(SearchEmployeeQuery query) {
        return Stream.empty();
    }
    Employee toQuery(freelance.domain.models.entity.Employee employee){
        User user =userRepository.getById(employee.getUserId());
        return ZMapUtils.toQuery(employee,user);
    }
}
