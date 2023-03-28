package freelance.infrastructure.queryService;

import freelance.domain.core.entity.User;
import freelance.domain.core.objetValue.EmployeeId;
import freelance.domain.output.repository.EmployeeRepository;
import freelance.domain.output.repository.UserRepository;
import freelance.application.query.EmployeeQueryService;
import freelance.application.query.model.Employee;
import freelance.application.query.query.SearchEmployeeQuery;
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
    Employee toQuery(freelance.domain.core.entity.Employee employee){
        User user =userRepository.getById(employee.getUserId());
        return ZMapUtils.toQuery(employee,user);
    }
}
