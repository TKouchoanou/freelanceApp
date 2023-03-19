package service.command;

import freelance.service.command.CommandException;
import freelance.service.command.command.company.CreateCompanyCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompanyCommandTest {
    @Test
    public void  createValidCompany(){
        CreateCompanyCommand command= CreateCompanyCommand
                .builder()
                .ribId(1L)
                .build();
        Assertions.assertThrows(CommandException.class, command::validateStateBeforeHandling);
        command.setName("TK Buisness");
        Assertions.assertDoesNotThrow(command::validateStateBeforeHandling);
        Assertions.assertThrows(CommandException.class, command::validateStateAfterHandling);
        command.setCompanyId(1L);
        Assertions.assertDoesNotThrow(command::validateStateAfterHandling);
    }
}
