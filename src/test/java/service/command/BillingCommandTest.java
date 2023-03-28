package service.command;

import freelance.application.command.CommandException;
import freelance.application.command.command.billing.CreateBillingCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillingCommandTest {
    @Test
    public void  createValidBilling(){
        CreateBillingCommand command= CreateBillingCommand
                .builder()
                .userId(1L)
                .amountHT(new BigDecimal(300))
                .amountTTC(new BigDecimal(200))
                .file(new byte[2])
                .ribId(1L)
                .startedDate(LocalDate.now())
                .endedDate(LocalDate.now())
                .fileName("facture_1")
                .build();
        Assertions.assertThrows(CommandException.class, command::validateStateBeforeHandling);
        command.setAmountTTC(new BigDecimal(350));
        Assertions.assertDoesNotThrow(command::validateStateBeforeHandling);
        Assertions.assertThrows(CommandException.class, command::validateStateAfterHandling);
        command.setBillingId(1L);
        command.setPaymentStatus("WAITING_VALIDATION");
        command.setValidationStatus("IS_PROCESSING");
        Assertions.assertDoesNotThrow(command::validateStateAfterHandling);
    }
}
