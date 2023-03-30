package freelance.interfaces.ui.billing.dto;

import freelance.application.query.model.BillingSummary;
import lombok.Getter;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
@Getter
public class BillingSummaryDto {

    Long id;
    LocalDate started;
    LocalDate ended;
    Money amountTTC;
    Money amountHT;
    String fileUri;// à revoir, car l'url peut varier d'une couche de presentation à une autre
    String paymentStatus;
    String validationStatus;

    public BillingSummaryDto(BillingSummary billingSummary) {
        this.id= billingSummary.getId();
        this.started=billingSummary.getStarted();
        this.ended=billingSummary.getEnded();
        this.amountHT = billingSummary.getAmountHT();
        this.amountTTC=billingSummary.getAmountTTC();
        this.fileUri="/billings/file/"+this.id;
        this.validationStatus= billingSummary.getValidationStatus();
        this.paymentStatus= billingSummary.getPaymentStatus();
    }
}
