package freelance.infrastructure.queryService;

import freelance.domain.core.objetValue.CompanyId;
import freelance.domain.output.repository.CompanyRepository;
import freelance.application.query.CompanyQueryService;
import freelance.application.query.model.Company;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;
@Service
public class CompanyQueryServiceImpl implements CompanyQueryService {
    CompanyRepository companyRepository;

    public CompanyQueryServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Optional<Company> getById(Long id) {
        return companyRepository.findById(new CompanyId(id))
                .map(this::toQuery);
    }

    @Override
    public Stream<Company> getAll() {
        return companyRepository.findAll().map(this::toQuery);
    }
    Company toQuery(freelance.domain.core.entity.Company domainCompany){
        return Company
                .builder()
                .id(domainCompany.getId().id())
                .name(domainCompany.getName())
                .ribId(domainCompany.getRibId().value())
                .build();
    }
}
