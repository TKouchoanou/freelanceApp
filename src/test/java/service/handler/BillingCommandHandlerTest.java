package service.handler;

import freelance.Application;
import freelance.application.command.CommandManager;
import freelance.application.command.command.billing.*;
import freelance.application.command.command.company.CreateCompanyCommand;
import freelance.application.command.command.rib.CreateRibCommand;
import freelance.application.command.command.user.CreateUserCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest(classes= Application.class)
public class BillingCommandHandlerTest {
    @Autowired
    private CommandManager commandManager;
    @Test
    public void  createWithSuccess(){
        CreateBillingCommand command= CreateBillingCommand
                .builder()
                .userId(createUser().getId())
                .amountHT(new BigDecimal(300))
                .amountTTC(new BigDecimal(500))
                .file(new byte[2])
                .ribId(createRib().getCreateRibId())
                .startedDate(LocalDate.now())
                .endedDate(LocalDate.now())
                .fileName("facture_1")
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(command));
        Assertions.assertNotEquals(null, command.getBillingId());
        Assertions.assertNotEquals(0L,command.getBillingId());

    }
    @Test
    public void  updateWithSuccess(){
        CreateBillingCommand command= CreateBillingCommand
                .builder()
                .userId(createUser().getId())
                .amountHT(new BigDecimal(300))
                .amountTTC(new BigDecimal(500))
                .file(new byte[2])
                .ribId(createRib().getCreateRibId())
                .companyId(createCompany().getCompanyId())
                .startedDate(LocalDate.now())
                .endedDate(LocalDate.now())
                .fileName("facture_1")
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(command));
        BigDecimal ht=new BigDecimal(1000);
        BigDecimal ttc=new BigDecimal(1050);
        UpdateBillingCommand updateBillingCommand =UpdateBillingCommand
                .builder()
                .billingId(command.getBillingId())
                .companyId(command.getCompanyId())
                .ribId(command.getRibId())
                .amountHT(ht)
                .amountTTC(ttc)
                .file(command.getFile())
                .fileName(command.getFileName())
                .startedDate(command.getStartedDate())
                .endedDate(command.getEndedDate())
                .build();
        Assertions.assertEquals(command.getBillingId(),updateBillingCommand.getBillingId());
        Assertions.assertNotEquals(updateBillingCommand.getAmountTTC(),command.getAmountTTC());
        Assertions.assertNotEquals(updateBillingCommand.getAmountHT(),command.getAmountHT());

    }
    @Test
    public void  updateUserWithSuccess(){
        CreateBillingCommand command= CreateBillingCommand
                .builder()
                .userId(createUser().getId())
                .amountHT(new BigDecimal(300))
                .amountTTC(new BigDecimal(500))
                .file(new byte[2])
                .ribId(createRib().getCreateRibId())
                .companyId(createCompany().getCompanyId())
                .startedDate(LocalDate.now())
                .endedDate(LocalDate.now())
                .fileName("facture_1")
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(command));
       CreateUserCommand createdUser=createUser();
        UpdateBillingUserCommand updateBillingUserCommand = UpdateBillingUserCommand
                .builder()
                .billingId(command.getBillingId())
                .userId(createdUser.getId())
                .build();

        Assertions.assertDoesNotThrow(()->commandManager.process(updateBillingUserCommand));
        Assertions.assertEquals(command.getBillingId(),updateBillingUserCommand.getBillingId());
        Assertions.assertEquals(createdUser.getId(),updateBillingUserCommand.getUserId());
        Assertions.assertNotEquals(updateBillingUserCommand.getUserId(),command.getUserId());

        String validationStatus="VALIDATE";
        UpdateValidationStatusCommand updateValidationStatusCommand= UpdateValidationStatusCommand
                .builder()
                .validationStatus(validationStatus)
                .billingId(command.getBillingId())
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(updateValidationStatusCommand));
        Assertions.assertNotEquals(command.getValidationStatus(),updateValidationStatusCommand.getValidationStatus());
        Assertions.assertEquals(validationStatus,updateValidationStatusCommand.getValidationStatus());
        String status="PAID";
        UpdatePaymentStatusCommand updatePaymentStatusCommand=UpdatePaymentStatusCommand
                .builder()
                .paymentStatus(status)
                .billingId(command.getBillingId())
                .build();
        Assertions.assertDoesNotThrow(()->commandManager.process(updatePaymentStatusCommand));
        Assertions.assertNotEquals(command.getPaymentStatus(),updatePaymentStatusCommand.getPaymentStatus());
        Assertions.assertEquals(status,updatePaymentStatusCommand.getPaymentStatus());
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


}
