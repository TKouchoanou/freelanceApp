package freelance.infrastructure.queryService;

import freelance.domain.core.objetValue.BillingId;
import freelance.domain.core.objetValue.CompanyId;
import freelance.domain.core.objetValue.RibId;
import freelance.domain.output.repository.BillingRepository;
import freelance.domain.output.repository.CompanyRepository;
import freelance.application.query.BillingQueryService;
import freelance.application.query.model.Billing;
import freelance.application.query.model.BillingSummary;
import freelance.application.query.query.SearchBillingQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
@Service
public class BillingQueryServiceImpl implements BillingQueryService {
    BillingRepository billingRepository;
    CompanyRepository companyRepository;
    BillingQueryServiceImpl(BillingRepository billingRepository, CompanyRepository companyRepository){
        this.billingRepository=billingRepository;
        this.companyRepository=companyRepository;
    }
    @Override
    public List<Billing> getAllBillings() {
        return billingRepository.findAll().map(this::toQuery).toList();
    }

    @Override
    public List<BillingSummary> getAllBillingsSummary() {
        return this.getAllBillings().stream().map(billing -> BillingSummary.builder()
                 .id(billing.getId())
                 .started(billing.getStarted())
                 .ended(billing.getEnded())
                 .paymentStatus(billing.getPaymentStatus())
                 .validationStatus(billing.getValidationStatus())
                 .amountHT(billing.getAmountHT())
                 .amountTTC(billing.getAmountTTC())
                 .fileUri("/erreer/")
                 .build()).toList();
    }

    @Override
    public Optional<Billing> getBillingById(Long id) {
        return billingRepository.findById(new BillingId(id))
                                 .map(this::toQuery);
    }

    @Override
    public Stream<Billing> searchBillingByRib(Long ribId) {
        return billingRepository.findByRibId(new RibId(ribId))
                                .map(this::toQuery);
    }

    @Override
    public Stream<Billing> searchBillingByCompany(Long companyId) {
        return billingRepository.findByCompanyId(new CompanyId(companyId))
                .map(this::toQuery);
    }

    @Override
    public Stream<Billing> searchBilling(SearchBillingQuery query) {
        return Stream.empty();
    }
    public  freelance.application.query.model.Billing toQuery(freelance.domain.core.entity.Billing billing){
        freelance.domain.core.entity.Company company=this.companyRepository.getById(billing.getCompanyId());
        return  freelance.application.query.model.
                Billing
                .builder()
                .id(billing.getId().id())
                .ended(billing.getPeriod().ended())
                .started(billing.getPeriod().started())
                .amountHT(billing.getAmountHT())
                .amountTTC(billing.getAmountTTC())
                .fileUri(billing.getId().id())
                .company(ZMapUtils.toQuery(company))
                .paymentStatus(billing.getPaymentStatus().name())
                .validationStatus(billing.getValidationStatus().name())
                .build();
    }

}
