package freelance.service.query.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
@Builder
@Getter
@Setter
public class BillingSummary {
    Long id;
    LocalDate started;
    LocalDate ended;
    Money amountTTC;
    Money amountHT;
    String fileUri;// à revoir, car l'url peut varier d'une couche de presentation à une autre
    String paymentStatus;
    String validationStatus;
}
