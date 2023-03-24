package freelance.domain.repository;

import freelance.domain.models.entity.Billing;
import freelance.domain.models.objetValue.BillingId;
import freelance.domain.models.objetValue.CompanyId;
import freelance.domain.models.objetValue.RibId;


import java.util.Set;
import java.util.stream.Stream;

public interface BillingRepository extends CrudRepository<Billing, BillingId>{
    Stream<Billing> findAll(Set<BillingId> billingIds);
    Stream<Billing> findByRibId(RibId ribId);
    Stream<Billing> findByCompanyId(CompanyId companyId);
}
