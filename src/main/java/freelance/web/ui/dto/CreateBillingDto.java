package freelance.web.ui.dto;

import freelance.service.command.command.billing.CreateBillingCommand;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    LocalDate startedDate;
    LocalDate endedDate;
    BigDecimal amountTTC;
    BigDecimal amountHT;
    MultipartFile[] file;
    String fileName;
    String validationStatus;
    String paymentStatus;
    public CreateBillingCommand toCommand()  {
        try {
            return CreateBillingCommand.builder()
                    .billingId(id)
                    .billingId(this.billingId)
                    .ribId(this.ribId)
                    .userId(userId)
                    .companyId(this.companyId)
                    .startedDate(this.startedDate)
                    .endedDate(this.endedDate)
                    .amountTTC(this.amountTTC)
                    .amountHT(this.amountHT)
                    .file(this.file != null && this.file.length > 0 ? this.file[0].getBytes() : null)
                    .fileName(this.fileName)
                    .validationStatus(this.validationStatus)
                    .paymentStatus(this.paymentStatus)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
