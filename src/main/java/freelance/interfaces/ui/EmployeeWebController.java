package freelance.interfaces.ui;

import freelance.application.command.CommandManager;
import freelance.application.command.command.employee.CreateEmployeeCommand;
import freelance.application.command.command.employee.UpdateEmployeeCommand;
import freelance.application.query.EmployeeQueryService;
import freelance.application.query.model.Employee;
import freelance.application.utils.DomainEnumMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeWebController {


    private final CommandManager commandManager;
    private final EmployeeQueryService employeeQueryService;

    public EmployeeWebController(CommandManager commandManager, EmployeeQueryService employeeQueryService) {
        this.commandManager = commandManager;
        this.employeeQueryService = employeeQueryService;
    }

    @GetMapping
    public String listEmployees(Model model) {
        List<Employee> employees = employeeQueryService.getAllEmployee();
        model.addAttribute("employees", employees);
        return "employee/employees";
    }

    @GetMapping("/{userId}/create")
    public String showCreateForm(@PathVariable Long userId, Model model) {
        model.addAttribute("createEmployeeCommand", CreateEmployeeCommand
                .builder().userId(userId).build());
        model.addAttribute("roles", DomainEnumMapper.employeeRoles().toList());
        return "employee/create-employee";
    }

    @PostMapping("/create")
    public String createEmployee(@Validated @ModelAttribute("employee") CreateEmployeeCommand command , BindingResult result) {
        commandManager.process(command);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
       UpdateEmployeeCommand updateEmployeeCommand=employeeQueryService
                .geEmployeeById(id)
                .map(employee ->UpdateEmployeeCommand.builder()
                        .employeeId(employee.getId())
                        .roles(employee.getRoles()).build()).orElseThrow();

        model.addAttribute("updateEmployeeCommand", updateEmployeeCommand);
        model.addAttribute("roles", DomainEnumMapper.employeeRoles().toList());
        return "employee/edit-employee";
    }

    @PostMapping("/{id}/edit")
    public String updateEmployee(@PathVariable("id") Long id,@Validated @ModelAttribute("employee") UpdateEmployeeCommand command, BindingResult result, Model model) {
        if (result.hasErrors()){
            model.addAttribute("updateEmployeeCommand", command);
            model.addAttribute("roles", DomainEnumMapper.employeeRoles().toList());
            return "employee/edit-employee";
        }
        commandManager.process(command);
        return "redirect:/employees";
    }

}
