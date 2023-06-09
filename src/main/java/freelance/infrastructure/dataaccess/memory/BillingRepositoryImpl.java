package freelance.infrastructure.dataaccess.memory;

import freelance.domain.core.entity.Billing;
import freelance.domain.core.objetValue.BillingId;
import freelance.domain.core.objetValue.CompanyId;
import freelance.domain.core.objetValue.RibId;
import freelance.domain.output.repository.BillingRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Stream;
@Repository
@SuppressWarnings("unchecked")
public class BillingRepositoryImpl implements BillingRepository {
    Map<BillingId, Billing> stores= new HashMap<>();
    @Override
    public Stream<Billing> findAll(Set<BillingId> billingIds) {
        return stores.values().stream().filter(b->billingIds.contains(b.getId()));
    }

    @Override
    public Stream<Billing> findByRibId(RibId ribId) {
         return stores.values().stream().filter(b->b.getRibId().equals(ribId));
    }

    @Override
    public Stream<Billing> findByCompanyId(CompanyId companyId) {
        return stores.values().stream().filter(b->b.getCompanyId().equals(companyId));
    }


    @Override
    public Stream<Billing> findAll() {
        return stores.values().stream();
    }

    @Override
    public Stream<Billing> findBy(Billing example) {
        return null;
    }

    @Override
    public Optional<Billing> findById(BillingId billingId) {
        return stores.values()
                .stream()
                .filter(b->billingId.equals(b.getId()))
                .findAny();
    }

    @Override
    public <S extends Billing> S save(S billing) {
        Billing saved;
        if(billing.getId()==null){
            BillingId id=nextId();
            saved=new Billing(id,billing.getUserId(),billing.getRibId(),billing.getCompanyId()
                    ,billing.getPeriod(),billing.getAmountTTC(),billing.getAmountHT(),billing.getFile()
                    , billing.getPaymentStatus(),billing.getValidationStatus(),billing.getCreatedDate(),billing.getUpdatedDate());
        }else {
            saved= new Billing(billing.getId(),billing.getUserId(),billing.getRibId(),billing.getCompanyId()
                    ,billing.getPeriod(),billing.getAmountTTC(),billing.getAmountHT(),billing.getFile()
                    , billing.getPaymentStatus(),billing.getValidationStatus(),billing.getCreatedDate(),billing.getUpdatedDate());
        }
        stores.put(saved.getId(),saved);
        return (S) saved;
    }

    private BillingId nextId() {
        return stores
                .keySet()
                .stream()
                .map(BillingId::id)
                .max(Long::compareTo)
                .map(last->new BillingId(last+1))
                .orElse(new BillingId(1L));
    }

    @Override
    public void deleteById(BillingId billingId) {

    }

    @Override
    public <S extends Billing> List<S> saveAll(Iterable<S> entities) {
        return null;
    }
}
