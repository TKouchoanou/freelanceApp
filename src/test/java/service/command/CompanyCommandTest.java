package service.command;

import freelance.service.command.CommandException;
import freelance.service.command.command.company.CreateCompanyCommand;
import freelance.service.command.command.company.UpdateCompanyCommand;
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
   @Test
    public void  updateValidCompany(){
        CreateCompanyCommand command= CreateCompanyCommand
                .builder()
                .ribId(1L)
                .name("TK Buisness")
                .build();
        String currentName="TKS Buisness";

       UpdateCompanyCommand updateCompanyCommand= UpdateCompanyCommand.builder().build();
       Assertions.assertThrows(CommandException.class,updateCompanyCommand::validateStateBeforeHandling);
       updateCompanyCommand.setCompanyId(command.getCompanyId());
       Assertions.assertThrows(CommandException.class,updateCompanyCommand::validateStateBeforeHandling);
       updateCompanyCommand.setName(currentName);
       Assertions.assertThrows(CommandException.class,updateCompanyCommand::validateStateBeforeHandling);
       updateCompanyCommand.setRibId(command.getRibId());
       Assertions.assertDoesNotThrow(command::validateStateBeforeHandling);

        Assertions.assertDoesNotThrow(updateCompanyCommand::validateStateAfterHandling);
    }
}
