package freelance.service.command.handler.employee;

import freelance.domain.models.entity.Employee;
import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.EmployeeRole;
import freelance.domain.models.objetValue.UserId;
import freelance.domain.repository.EmployeeRepository;
import freelance.domain.repository.UserRepository;
import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.employee.CreateEmployeeCommand;
import org.springframework.stereotype.Service;
import freelance.service.command.Command.Usecase;

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
