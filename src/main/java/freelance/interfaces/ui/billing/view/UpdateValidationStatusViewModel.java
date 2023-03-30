package freelance.interfaces.ui.billing.view;

import freelance.application.command.command.billing.UpdateValidationStatusCommand;
import freelance.application.query.model.Billing;
import freelance.application.utils.DomainEnumMapper;
import freelance.interfaces.ui.billing.dto.BillingFreelanceSummaryDto;
import freelance.interfaces.ui.billing.dto.CreateBillingDto;
import lombok.*;
import org.javamoney.moneta.Money;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateValidationStatusViewModel {
    private UpdateValidationStatusCommand updateValidationStatusCommand;
    BillingFreelanceSummaryDto freelance;
    private Money amountHT;
    private Money amountTTC;
    private Date startedDate;
    private Date endedDate;
    private Set<String> statusList;

    public static UpdateValidationStatusViewModel createUpdateStatusValidationViewModelFrom(Billing billing) {
        UpdateValidationStatusCommand updateValidationStatusCommand =
                UpdateValidationStatusCommand.builder()
                        .billingId(billing.getId())
                        .validationStatus(billing.getValidationStatus())
                        .build();

        BillingFreelanceSummaryDto freelance= BillingFreelanceSummaryDto
                .builder()
                .email(billing.getFreelance().getEmail())
                .lastName(billing.getFreelance().getLastName())
                .companyName(billing.getFreelance().getCompanyName())
                .firstName(billing.getFreelance().getFirstName())
                .build();

        Money amountTTC = billing.getAmountTTC();
        Money amountHT = billing.getAmountHT();
        Date startedDate = CreateBillingDto.fromLocalDateToDate(billing.getStarted());
        Date endedDate = CreateBillingDto.fromLocalDateToDate(billing.getEnded());
        Set<String> statusList = DomainEnumMapper.billingValidationStatus().collect(Collectors.toSet());

        UpdateValidationStatusViewModel viewModel = new UpdateValidationStatusViewModel();
        viewModel.setUpdateValidationStatusCommand(updateValidationStatusCommand);
        viewModel.setFreelance(freelance);
        viewModel.setAmountHT(amountHT);
        viewModel.setAmountTTC(amountTTC);
        viewModel.setStartedDate(startedDate);
        viewModel.setEndedDate(endedDate);
        viewModel.setStatusList(statusList);

        return viewModel;
    }
}
