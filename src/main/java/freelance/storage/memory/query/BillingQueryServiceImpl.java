package freelance.storage.memory.query;

import freelance.domain.models.objetValue.BillingId;
import freelance.domain.models.objetValue.CompanyId;
import freelance.domain.models.objetValue.RibId;
import freelance.domain.repository.BillingRepository;
import freelance.domain.repository.CompanyRepository;
import freelance.service.query.BillingQueryService;
import freelance.service.query.model.Billing;
import freelance.service.query.model.BillingSummary;
import freelance.service.query.query.SearchBillingQuery;
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
    public  freelance.service.query.model.Billing toQuery(freelance.domain.models.entity.Billing billing){
        freelance.domain.models.entity.Company company=this.companyRepository.getById(billing.getCompanyId());
        return  freelance.service.query.model.
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
