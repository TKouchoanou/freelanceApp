package freelance.domain.models.entity;

import freelance.domain.models.objetValue.*;
import org.javamoney.moneta.Money;


public class Billing  extends Auditable{
    BillingId id;
    UserId freelanceId;
    Rib rib;
    Company company;
    Period period;
    Money amountTTC;
    Money amountHT;
    BillingFile file;
    PaymentStatus paymentStatus;
    ValidationStatus validationStatus;

}
