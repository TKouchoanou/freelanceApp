package service.command;

import freelance.application.command.CommandException;
import freelance.application.command.command.employee.CreateEmployeeCommand;
import freelance.application.command.command.employee.UpdateEmployeeCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeeCommandTest {
    @Test
    public void  createValidEmployee(){
        CreateEmployeeCommand command= CreateEmployeeCommand
                .builder()
                .userId(1L)
                .roles(List.of("FINANCE"))
                .build();
        Assertions.assertDoesNotThrow(command::validateStateBeforeHandling);
        Assertions.assertThrows(CommandException.class, command::validateStateAfterHandling);
        command.setEmployeeId(1L);
        Assertions.assertDoesNotThrow(command::validateStateAfterHandling);
    }
    @Test
    public void  updateValidEmployee(){
        UpdateEmployeeCommand command=   UpdateEmployeeCommand
                .builder()
                .roles(List.of("ADMIN"))
                .build();

        Assertions.assertThrows(CommandException.class, command::validateStateBeforeHandling);
        command.setEmployeeId(1L);
        Assertions.assertDoesNotThrow(command::validateStateBeforeHandling);
        Assertions.assertDoesNotThrow(command::validateStateAfterHandling);
    }
}
