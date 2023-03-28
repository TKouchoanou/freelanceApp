package freelance.application.command.handler.employee;

import freelance.domain.core.entity.Employee;
import freelance.domain.core.objetValue.EmployeeId;
import freelance.domain.core.objetValue.EmployeeRole;
import freelance.domain.output.repository.EmployeeRepository;
import freelance.domain.common.security.Auth;
import freelance.application.command.Command;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.employee.UpdateEmployeeCommand;
import freelance.application.command.utils.AuthProvider;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
@Service
public class UpdateEmployeeCommandHandler implements CommandHandler {
    EmployeeRepository employeeRepository;
    AuthProvider authProvider;
    UpdateEmployeeCommandHandler( EmployeeRepository employeeRepository,AuthProvider authProvider){
        this.employeeRepository=employeeRepository; this.authProvider=authProvider;
    }
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
