package freelance.interfaces.ui.billing.view;
import freelance.application.query.model.Billing;
import freelance.application.utils.DomainEnumMapper;
import freelance.interfaces.ui.billing.dto.BillingFreelanceSummaryDto;
import freelance.interfaces.ui.billing.dto.CreateBillingDto;
import lombok.*;
import org.javamoney.moneta.Money;

import freelance.application.command.command.billing.UpdatePaymentStatusCommand;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentStatusViewModel {
    private UpdatePaymentStatusCommand updatePaymentStatusCommand;
    BillingFreelanceSummaryDto freelance;
    private Money amountHT;
    private Money amountTTC;
    private Date startedDate;
    private Date endedDate;
    private Set<String> statusList;

    public UpdatePaymentStatusViewModel(Billing billing) {
        this.updatePaymentStatusCommand =
                UpdatePaymentStatusCommand.builder()
                        .billingId(billing.getId())
                        .paymentStatus(billing.getPaymentStatus())
                        .build();
          this.freelance= BillingFreelanceSummaryDto
                .builder()
                .email(billing.getFreelance().getEmail())
                .lastName(billing.getFreelance().getLastName())
                .companyName(billing.getFreelance().getCompanyName())
                .firstName(billing.getFreelance().getFirstName())
                .build();
        this.amountTTC = billing.getAmountTTC();
        this.amountHT = billing.getAmountHT();
        this.statusList = DomainEnumMapper.billingPaymentStatus().collect(Collectors.toSet());
        this.startedDate = CreateBillingDto.fromLocalDateToDate(billing.getStarted());
        this.endedDate =  CreateBillingDto.fromLocalDateToDate(billing.getEnded());
    }
}
