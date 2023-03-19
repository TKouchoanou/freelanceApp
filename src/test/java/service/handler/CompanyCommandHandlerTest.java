package service.handler;

import freelance.Application;
import freelance.service.command.CommandManager;
import freelance.service.command.command.company.CreateCompanyCommand;
import freelance.service.command.command.rib.CreateRibCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(classes= Application.class)
public class CompanyCommandHandlerTest {
    @Autowired
    private CommandManager commandManager;
    @Test
    public void  createWithSuccess(){
        CreateCompanyCommand command= CreateCompanyCommand
                .builder()
                .ribId(createRib().getCreateRibId())
                .name("TK Buisness")
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(command));
        Assertions.assertNotEquals(null, command.getCompanyId());
        Assertions.assertNotEquals(0L,command.getCompanyId());

    }

    public CreateRibCommand createRib(){
        CreateRibCommand command= CreateRibCommand
                .builder()
                .iban("FR7628521966142334508845009")
                .bic("ABCDEFGH")
                .cleRib("10")
                .build();
        commandManager.process(command);
        return command;
    }
}
