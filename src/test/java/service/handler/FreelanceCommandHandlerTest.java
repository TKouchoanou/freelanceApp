package service.handler;

import freelance.Application;
import freelance.application.command.CommandManager;
import freelance.application.command.command.company.CreateCompanyCommand;
import freelance.application.command.command.freeLance.CreateFreeLanceCommand;
import freelance.application.command.command.freeLance.UpdateFreeLanceCommand;
import freelance.application.command.command.rib.CreateRibCommand;
import freelance.application.command.command.user.CreateUserCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes= Application.class)
public class FreelanceCommandHandlerTest {
    @Autowired
    private CommandManager commandManager;
    @Test
    public void  createWithSuccess(){
        CreateFreeLanceCommand command= CreateFreeLanceCommand
                .builder()
                .userId(createUser().getId())
                .companyId(createCompany().getCompanyId())
                .ribId(createRib().getCreateRibId())
                .build();
        CreateFreeLanceCommand command2= CreateFreeLanceCommand
                .builder()
                .userId(createUser().getId())
                .companyId(createCompany().getCompanyId())
                .build();
        CreateFreeLanceCommand command3= CreateFreeLanceCommand
                .builder()
                .userId(createUser().getId())
                .ribId(createRib().getCreateRibId())
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(command));
        Assertions.assertNotEquals(null, command.getId());
        Assertions.assertNotEquals(0L,command.getId());
        Assertions.assertDoesNotThrow(()->commandManager.process(command2));
        Assertions.assertDoesNotThrow(()->commandManager.process(command3));
    }

    @Test
    public void  updateWithSuccess(){
        CreateFreeLanceCommand command= CreateFreeLanceCommand
                .builder()
                .userId(createUser().getId())
                .companyId(createCompany().getCompanyId())
                .ribId(createRib().getCreateRibId())
                .build();

        Assertions.assertDoesNotThrow(()->commandManager.process(command));
        CreateRibCommand newRib=createRib("FR1420041010050500013M02606");
        UpdateFreeLanceCommand updateCommand= UpdateFreeLanceCommand
                .builder()
                .companyId(command.getCompanyId())
                .Id(command.getId())
                .ribId(newRib.getCreateRibId())
                .build();

        Assertions.assertDoesNotThrow(()->commandManager.process(updateCommand));
        Assertions.assertSame(updateCommand.getRibId(),newRib.getCreateRibId());
        Assertions.assertSame(updateCommand.getId(), command.getId());

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
    public CreateRibCommand createRib(String iban){
        CreateRibCommand command= CreateRibCommand
                .builder()
                .iban(iban)
                .bic("ABCDEFGH")
                .cleRib("10")
                .build();
        commandManager.process(command);
        return command;
    }

    public CreateCompanyCommand createCompany(){
        CreateCompanyCommand command= CreateCompanyCommand
                .builder()
                .ribId(createRib().getCreateRibId())
                .name("TK Buisness")
                .build();
        commandManager.process(command);
        return command;
    }
}
