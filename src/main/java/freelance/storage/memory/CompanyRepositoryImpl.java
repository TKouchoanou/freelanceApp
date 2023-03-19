package freelance.storage.memory;

import freelance.domain.models.entity.Company;
import freelance.domain.models.objetValue.CompanyId;
import freelance.domain.repository.CompanyRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
@Repository
@SuppressWarnings("unchecked")
public class CompanyRepositoryImpl implements CompanyRepository {
    Map<CompanyId, Company> stores= new HashMap<>();
    @Override
    public Stream<Company> findAll() {
        return stores.values().stream();
    }

    @Override
    public Stream<Company> findBy(Company example) {
        return null;
    }

    @Override
    public Optional<Company> findById(CompanyId companyId) {
        return stores.values().stream().filter(c->companyId.equals(c.getId())).findAny();
    }

    @Override
    public <S extends Company> S save(S company) {
        Company saved;
        if(company.getId()==null){
            CompanyId id=nextId();
            saved=new Company(id, company.getName(), company.getRibId(),company.getFreelanceIds(),company.getBillingIds(),company.getCreatedDate(),company.getUpdatedDate());
        }else {
            saved=new Company(company.getId(), company.getName(), company.getRibId(),company.getFreelanceIds(),company.getBillingIds(),company.getCreatedDate(),company.getUpdatedDate());
        }
        stores.put(saved.getId(),saved);
        return (S) saved;
    }

    private CompanyId nextId() {
        return stores
                .keySet()
                .stream()
                .map(CompanyId::id)
                .max(Long::compareTo)
                .map(last->new CompanyId(last+1))
                .orElse(new CompanyId(1L));
    }

    @Override
    public void deleteById(CompanyId companyId) {

    }

    @Override
    public <S extends Company> List<S> saveAll(Iterable<S> entities) {
        return null;
    }
}
