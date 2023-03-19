package service.handler;

import freelance.Application;
import freelance.service.command.CommandManager;
import freelance.service.command.command.rib.CreateRibCommand;
import freelance.service.command.command.rib.UpdateRibCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes=Application.class)
public class RibCommandHandlerTest {
    @Autowired
    private CommandManager commandManager;
    @Test
    public void  createWithSuccess(){
        CreateRibCommand command= CreateRibCommand
                .builder()
                .iban("FR7628521966142334508845009")
                .bic("ABCDEFGH")
                .cleRib("10")
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(command));
        Assertions.assertNotNull(command.getCreateRibId());//repository test
    }

    @Test
    public void  updateWithSuccess(){
        String userName="Theophane Kouchoanou";
        CreateRibCommand createCommand = CreateRibCommand
                .builder()
                .iban("FR7628521966142334508845009")
                .bic("ABCDEFGH")
                .cleRib("10")
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(createCommand));


        UpdateRibCommand updateCommand =UpdateRibCommand
                .builder()
                .ribId(createCommand.getRibId())
                .cleRib(createCommand.getCleRib())
                .bic(createCommand.getBic())
                .iban(createCommand.getIban())
                .username(userName)
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(updateCommand));
        Assertions.assertSame(updateCommand.getUsername(),userName);
        Assertions.assertSame(updateCommand.getRibId(), createCommand.getRibId());

    }
}
