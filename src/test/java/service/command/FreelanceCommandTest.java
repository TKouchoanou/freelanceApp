package service.command;

import freelance.service.command.CommandException;
import freelance.service.command.command.employee.CreateEmployeeCommand;
import freelance.service.command.command.freeLance.CreateFreeLanceCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FreelanceCommandTest {
    @Test
    public void  createValidFreelance(){
        CreateFreeLanceCommand command= CreateFreeLanceCommand
                .builder()
                .userId(1L)
                .build();
        Assertions.assertThrows(CommandException.class, command::validateStateBeforeHandling);
        command.setRibId(1L);
        Assertions.assertDoesNotThrow(command::validateStateBeforeHandling);
        command.setCompanyId(1L);
        Assertions.assertDoesNotThrow(command::validateStateBeforeHandling);

        Assertions.assertDoesNotThrow(CreateFreeLanceCommand.builder().userId(1L).companyId(1L).build()::validateStateBeforeHandling);
        Assertions.assertDoesNotThrow(command::validateStateBeforeHandling);
        command.setFreeLanceId(1L);
        Assertions.assertDoesNotThrow(command::validateStateAfterHandling);
    }
}
