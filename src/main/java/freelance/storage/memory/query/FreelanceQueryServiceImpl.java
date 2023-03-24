package freelance.storage.memory.query;

import freelance.domain.models.entity.Rib;
import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.CompanyId;
import freelance.domain.models.objetValue.FreelanceId;
import freelance.domain.models.objetValue.RibId;
import freelance.domain.repository.*;
import freelance.service.query.FreelanceQueryService;
import freelance.service.query.model.BillingSummary;
import freelance.service.query.model.Freelance;
import freelance.service.query.model.FreelanceSummary;
import freelance.service.query.query.SearchFreelanceQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class FreelanceQueryServiceImpl implements FreelanceQueryService {
    FreelanceRepository freelanceRepository;
    UserRepository userRepository;
    RibRepository ribRepository;

    CompanyRepository companyRepository;
   BillingRepository billingRepository;

    FreelanceQueryServiceImpl(FreelanceRepository freelanceRepository, UserRepository userRepository,
                              RibRepository ribRepository,CompanyRepository companyRepository,BillingRepository billingRepository){

        this.freelanceRepository=freelanceRepository;
        this.userRepository=userRepository;
        this.ribRepository=ribRepository;
        this.companyRepository=companyRepository;
        this.billingRepository=billingRepository;
    }
    @Override
    public List<FreelanceSummary> getAllFreelances() {
        return freelanceRepository.findAll()
                .map(this::toSummaryQuery)
                .toList();
    }

    @Override
    public Optional<Freelance> getFreelanceById(Long id) {
        return freelanceRepository.findById(new FreelanceId(id))
                                  .map(this::toQuery);
    }

    @Override
    public Stream<FreelanceSummary> searchFreelanceByRib(Long ribId) {
        return freelanceRepository.findByRibId(new RibId(ribId))
                                  .map(this::toSummaryQuery);
    }

    @Override
    public Stream<FreelanceSummary> searchFreelanceByCompany(Long companyId) {
        return freelanceRepository.findByCompany(new CompanyId(companyId))
                                  .map(this::toSummaryQuery);
    }

    @Override
    public Stream<FreelanceSummary> searchFreelance(SearchFreelanceQuery query) {
        return Stream.<FreelanceSummary>builder().build();
    }

    Freelance toQuery(freelance.domain.models.entity.Freelance freelance){
        User user =userRepository.getById(freelance.getUserId());
        Rib rib=ribRepository.getById(freelance.getRibId());
        freelance.domain.models.entity.Company company=companyRepository.getById(freelance.getCompanyId());
         Set<BillingSummary> billings = billingRepository
                 .findAll(freelance.getBillingIds())
                 .map(ZMapUtils::toQuery)
                 .collect(Collectors.toSet());

        return  ZMapUtils.toQuery(freelance,user,rib,company,billings);
    }

    FreelanceSummary toSummaryQuery(freelance.domain.models.entity.Freelance freelance){
        User user =userRepository.getById(freelance.getUserId());
        freelance.domain.models.entity.Company company=companyRepository.getById(freelance.getCompanyId());
        return ZMapUtils.toQuery(freelance,user,company);
    }


}
