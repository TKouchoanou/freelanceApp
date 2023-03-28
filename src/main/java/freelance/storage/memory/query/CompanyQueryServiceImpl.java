package freelance.storage.memory.query;

import freelance.domain.models.objetValue.CompanyId;
import freelance.domain.repository.CompanyRepository;
import freelance.service.query.CompanyQueryService;
import freelance.service.query.model.Company;
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
    Company toQuery(freelance.domain.models.entity.Company domainCompany){
        return Company
                .builder()
                .id(domainCompany.getId().id())
                .name(domainCompany.getName())
                .ribId(domainCompany.getRibId().value())
                .build();
    }
}
