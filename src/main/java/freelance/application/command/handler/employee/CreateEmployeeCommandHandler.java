package freelance.application.command.handler.employee;

import freelance.domain.core.entity.Employee;
import freelance.domain.core.entity.User;
import freelance.domain.core.objetValue.EmployeeRole;
import freelance.domain.core.objetValue.UserId;
import freelance.domain.output.repository.EmployeeRepository;
import freelance.domain.output.repository.UserRepository;
import freelance.application.command.Command;
import freelance.application.command.CommandException;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.employee.CreateEmployeeCommand;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
@Service
public class CreateEmployeeCommandHandler implements CommandHandler {
    UserRepository userRepository;
    EmployeeRepository employeeRepository;
    CreateEmployeeCommandHandler( UserRepository userRepository, EmployeeRepository employeeRepository){
        this.userRepository=userRepository;
        this.employeeRepository=employeeRepository;
    }
    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof CreateEmployeeCommand cmd)){
            return;
        }
        UserId userId= new UserId(cmd.getUserId());
        var optionalEmployee= employeeRepository.findByUserId(userId);
        optionalEmployee.ifPresent(employee -> {
            throw new CommandException("employee profile already exist with this user, it's id is "+employee.getId().value());
        });

        Set<EmployeeRole> roles= cmd.getRoles().stream().map(EmployeeRole::valueOf).collect(Collectors.toSet());
        User user=userRepository.getById(new UserId(cmd.getUserId()));
        Employee employee = new Employee(user,roles);
        Employee result=  employeeRepository.save(employee);
        cmd.setEmployeeId(result.getId().value());
    }
}
