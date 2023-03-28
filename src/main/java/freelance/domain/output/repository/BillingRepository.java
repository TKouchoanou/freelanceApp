package freelance.domain.output.repository;

import freelance.domain.core.entity.Billing;
import freelance.domain.core.objetValue.BillingId;
import freelance.domain.core.objetValue.CompanyId;
import freelance.domain.core.objetValue.RibId;


import java.util.Set;
import java.util.stream.Stream;

public interface BillingRepository extends CrudRepository<Billing, BillingId>{
    Stream<Billing> findAll(Set<BillingId> billingIds);
    Stream<Billing> findByRibId(RibId ribId);
    Stream<Billing> findByCompanyId(CompanyId companyId);
}
