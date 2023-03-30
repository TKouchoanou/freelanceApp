package freelance.infrastructure.queryService;


import freelance.application.query.FreelanceQueryService;
import freelance.application.query.model.File;
import freelance.application.query.model.FreelanceSummary;
import freelance.domain.core.entity.Freelance;
import freelance.domain.core.objetValue.BillingFile;
import freelance.domain.core.objetValue.BillingId;
import freelance.domain.core.objetValue.CompanyId;
import freelance.domain.core.objetValue.RibId;
import freelance.domain.output.files.FileStorageService;
import freelance.domain.output.repository.BillingRepository;
import freelance.domain.output.repository.CompanyRepository;
import freelance.application.query.BillingQueryService;
import freelance.application.query.model.Billing;
import freelance.application.query.model.BillingSummary;
import freelance.application.query.query.SearchBillingQuery;
import freelance.domain.output.repository.FreelanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
@Service
public class BillingQueryServiceImpl implements BillingQueryService {
    BillingRepository billingRepository;
   FileStorageService fileStorageService;
    CompanyRepository companyRepository;
    FreelanceRepository freelanceRepository;

    FreelanceQueryService freelanceQueryService;

    BillingQueryServiceImpl(BillingRepository billingRepository, CompanyRepository companyRepository, FileStorageService fileStorageService){
        this.billingRepository=billingRepository;
        this.companyRepository=companyRepository;
        this.fileStorageService=fileStorageService;
    }
   @Autowired
    public BillingQueryServiceImpl(BillingRepository billingRepository, FileStorageService fileStorageService, CompanyRepository companyRepository, FreelanceRepository freelanceRepository,FreelanceQueryService freelanceQueryService) {
        this.billingRepository = billingRepository;
        this.fileStorageService = fileStorageService;
        this.companyRepository = companyRepository;
        this.freelanceRepository = freelanceRepository;
        this.freelanceQueryService=freelanceQueryService;
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

    @Override
    public File loadBillingFile(Long id) {
        freelance.domain.core.entity.Billing billing=billingRepository.getById(new BillingId(id));
        BillingFile billingFile= billing.getFile();
        freelance.domain.core.objetValue.File file = fileStorageService.load(billingFile.fileId(), billingFile.context());
        return File.builder()
                .name(billingFile.originalName())
                .inputStream(new BufferedInputStream(new ByteArrayInputStream(file.file())))
                .build();
    }

    public  freelance.application.query.model.Billing toQuery(freelance.domain.core.entity.Billing billing){
        freelance.domain.core.entity.Company company=this.companyRepository.getById(billing.getCompanyId());
        System.out.println("user id "+billing.getUserId());
        Freelance freelanceFound=freelanceRepository
                .findByUserId(billing.getUserId())
                .orElseThrow();
    FreelanceSummary freelanceSummary=freelanceQueryService
            .getAllFreelances()
            .stream()
            .filter(fs-> fs.getId().equals(freelanceFound.getId().value()))
            .findAny().orElseThrow();
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
                .freelance(freelanceSummary)
                .build();
    }

}
