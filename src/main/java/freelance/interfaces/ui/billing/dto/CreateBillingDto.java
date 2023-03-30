package freelance.interfaces.ui.billing.dto;

import freelance.application.command.command.billing.CreateBillingCommand;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBillingDto {
    Long id;
    Long billingId;
    Long ribId;
    Long userId;
    Long companyId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date endedDate;
    BigDecimal amountTTC;
    BigDecimal amountHT;
    MultipartFile[] file;
    String fileName;
    String validationStatus;
    String paymentStatus;

    public CreateBillingDto(CreateBillingCommand createBillingCommand) {
         this.id=createBillingCommand.getBillingId();
         this.billingId=createBillingCommand.getBillingId();
         this.ribId=createBillingCommand.getRibId();
         this.userId=createBillingCommand.getUserId();
         this.companyId=createBillingCommand.getCompanyId();
         this.validationStatus= createBillingCommand.getValidationStatus();
         this.paymentStatus= createBillingCommand.getPaymentStatus();
         this.startedDate=fromLocalDateToDate(createBillingCommand.getStartedDate());
         this.endedDate=fromLocalDateToDate(createBillingCommand.getEndedDate());
         this.amountHT = createBillingCommand.getAmountHT();
         this.amountTTC=createBillingCommand.getAmountTTC();
    }

    public CreateBillingCommand toCommand()  {
        try {
            return CreateBillingCommand.builder()
                    .billingId(id)
                    .billingId(this.billingId)
                    .ribId(this.ribId)
                    .userId(userId)
                    .companyId(this.companyId)
                    .startedDate(fromDateToLocalDate(this.startedDate))
                    .endedDate(fromDateToLocalDate(this.endedDate))
                    .amountTTC(this.amountTTC)
                    .amountHT(this.amountHT)
                    .file(this.file != null && this.file.length > 0 ? this.file[0].getBytes() : null)
                    .fileName(this.file[0].getOriginalFilename())
                    .validationStatus(this.validationStatus)
                    .paymentStatus(this.paymentStatus)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public  static LocalDate fromDateToLocalDate(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime.toLocalDate();
    }

    public static  Date fromLocalDateToDate(LocalDate localDate){
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
       return Date.from(instant);
    }
}
