package service.command;

import freelance.service.command.command.rib.CreateRibCommand;
import freelance.service.command.CommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class RibCommandTest {

    @Test
    public void  createValidCommand(){
        CreateRibCommand command= CreateRibCommand
                .builder()
                .iban("FR7628521966142334508845009")
                .bic("ABCDEFGH")
                .cleRib("10")
                .build();
        Assertions.assertDoesNotThrow(command::validateStateBeforeHandling);
        Assertions.assertThrows(CommandException.class, command::validateStateAfterHandling);
        command.setCreateRibId(1L);
        Assertions.assertDoesNotThrow(command::validateStateAfterHandling);

    }
    @Test
    public void  createInValidCommand(){
        CreateRibCommand command= CreateRibCommand
                .builder()
                .iban("    ")
                .bic("ABCDEFGH")
                .cleRib("10")
                .build();
        Assertions.assertThrows(CommandException.class,command::validateStateBeforeHandling);
    }

}
