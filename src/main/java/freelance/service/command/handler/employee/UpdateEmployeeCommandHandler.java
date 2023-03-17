package freelance.service.command.handler.employee;

import freelance.domain.models.entity.Employee;
import freelance.domain.models.objetValue.EmployeeId;
import freelance.domain.models.objetValue.EmployeeRole;
import freelance.domain.repository.EmployeeRepository;
import freelance.domain.security.Auth;
import freelance.service.command.Command;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.employee.UpdateEmployeeCommand;
import freelance.service.command.Command.Usecase;
import freelance.service.command.utils.AuthProvider;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
@Service
public class UpdateEmployeeCommandHandler implements CommandHandler {
    EmployeeRepository employeeRepository;
    AuthProvider authProvider;
    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof UpdateEmployeeCommand cmd)){
            return;
        }
        Auth auth= authProvider.getCurrentAuth();
        Employee employee=employeeRepository.getById(new EmployeeId(cmd.getEmployeeId()));
        Set<EmployeeRole> oldRoles = employee.getEmployeeRoles();
        Set<EmployeeRole> newRoles=cmd.getRoles().stream().map(EmployeeRole::valueOf).collect(Collectors.toSet());
        Set<EmployeeRole> roleToRemove= oldRoles.stream().filter(role -> !newRoles.contains(role)).collect(Collectors.toSet());

        newRoles.forEach(role -> employee.addRole(role,auth));
        //suppression des roles autrefois existante mais absence de la nouvelle requête. it's a PUT
        roleToRemove.forEach(role -> employee.removeRole(role,auth));
    }
}
