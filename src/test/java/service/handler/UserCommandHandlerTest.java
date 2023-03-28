package service.handler;

import freelance.Application;
import freelance.service.command.CommandManager;
import freelance.service.command.command.user.ChangePassWordCommand;
import freelance.service.command.command.user.CreateUserCommand;
import freelance.service.command.command.user.UpdateUserCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes= Application.class)
public class UserCommandHandlerTest {
    @Autowired
    private CommandManager commandManager;

    @Test
    public void  createWithSuccess(){
        CreateUserCommand command= CreateUserCommand
                .builder()
                .passWord("toto")
                .email("ptitkouche@yahoo.fr")
                .firstName("malo")
                .lastName("malo")
                .isActive(true)
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(command));
        Assertions.assertNotEquals(null, command.getId());//repository test
        Assertions.assertNotEquals(0L,command.getId());

    }

    @Test
    public void  updateWithSuccess(){
        CreateUserCommand createCommand= CreateUserCommand
                .builder()
                .passWord("toto")
                .email("ptitkouche@yahoo.fr")
                .firstName("malo")
                .lastName("malo")
                .isActive(true)
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(createCommand));
        String newLastName="theo";
        UpdateUserCommand updateCommand= UpdateUserCommand
                .builder()
                .email(createCommand.getEmail())
                .firstName(createCommand.getFirstName())
                .lastName(newLastName)
                .isActive(true)
                .id(createCommand.getId())
                .build();

        Assertions.assertDoesNotThrow(()->commandManager.process(updateCommand));
        Assertions.assertSame(updateCommand.getLastName(),newLastName);
        Assertions.assertSame(updateCommand.getId(), createCommand.getId());

    }
    @Test
    public void  changePassWordWithSuccess(){
       CreateUserCommand createCommand= CreateUserCommand
                .builder()
                .passWord("toto")
                .email("ptitkouche@yahoo.fr")
                .firstName("theo")
                .lastName("malo")
                .isActive(true)
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(createCommand));
        String password="tata";
        ChangePassWordCommand changePassWordCommand= ChangePassWordCommand
                .builder()
                .userId(createCommand.getId())
                .newPasswordConfirmation(password)
                .newPassword(password)
                .build();

        Assertions.assertDoesNotThrow(()->commandManager.process(changePassWordCommand));
        Assertions.assertTrue(changePassWordCommand.hasSucceed());
        Assertions.assertSame(changePassWordCommand.getUserId(), createCommand.getId());

    }
}
