package service.handler;

import freelance.Application;
import freelance.application.command.CommandManager;
import freelance.application.command.command.employee.CreateEmployeeCommand;
import freelance.application.command.command.employee.UpdateEmployeeCommand;
import freelance.application.command.command.user.CreateUserCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes= Application.class)
public class EmployeeCommandHandlerTest {
    @Autowired
    private CommandManager commandManager;
    @Test
    public void  createWithSuccess(){
        CreateEmployeeCommand command= CreateEmployeeCommand
                .builder()
                .userId(createUser().getId())
                .roles(List.of("ADMIN"))
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(command));
        Assertions.assertNotEquals(null, command.getEmployeeId());//repository test
        Assertions.assertNotEquals(0L,command.getEmployeeId());
    }

    public CreateUserCommand createUser(){
        CreateUserCommand command= CreateUserCommand
                .builder()
                .passWord("toto")
                .email("ptitkouche@yahoo.fr")
                .firstName("malo")
                .lastName("malo")
                .isActive(true)
                .build();
        commandManager.process(command);
        return command;
    }

    @Test
    public void  updateWithSuccess(){
        CreateEmployeeCommand createCommand= CreateEmployeeCommand
                .builder()
                .userId(createUser().getId())
                .roles(List.of("ADMIN"))
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(createCommand));
        List<String> roles=List.of("ADMIN","FINANCE");
        UpdateEmployeeCommand updateCommand= UpdateEmployeeCommand
                .builder()
                .roles(new HashSet<>(roles))
                .employeeId(createCommand.getEmployeeId())
                .build();

        Assertions.assertDoesNotThrow(()->commandManager.process(updateCommand));
        Assertions.assertSame(updateCommand.getRoles(),roles);
        Assertions.assertSame(updateCommand.getEmployeeId(), createCommand.getEmployeeId());

    }
}
