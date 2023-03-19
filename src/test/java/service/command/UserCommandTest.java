package service.command;

import freelance.service.command.CommandException;
import freelance.service.command.command.user.CreateUserCommand;
import freelance.service.command.command.user.UpdateUserCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserCommandTest {
   @Test
    public void  createValidUser(){
        CreateUserCommand command= CreateUserCommand
                .builder()
                .passWord("toto")
                .email("ptitkouche@yahoo.fr")
                .firstName("malo")
                .lastName("malo")
                .isActive(true)
                .build();
        Assertions.assertDoesNotThrow(command::validateStateBeforeHandling);
        Assertions.assertThrows(CommandException.class, command::validateStateAfterHandling);
        command.setId(1L);
        Assertions.assertDoesNotThrow(command::validateStateAfterHandling);
    }
    @Test
    public void  updateValidUser(){
        UpdateUserCommand command= UpdateUserCommand
                .builder()
                .email("ptitkouche@yahoo.fr")
                .firstName("malo")
                .lastName("malo")
                .isActive(true)
                .id(1L)
                .build();
        Assertions.assertDoesNotThrow(command::validateStateBeforeHandling);
        Assertions.assertDoesNotThrow(command::validateStateAfterHandling);
    }
}
