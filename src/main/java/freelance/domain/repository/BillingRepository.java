package freelance.domain.repository;

import freelance.domain.models.entity.Billing;
import freelance.domain.models.objetValue.BillingId;


import java.util.Set;
import java.util.stream.Stream;

public interface BillingRepository extends CrudRepository<BillingId, Billing>{
    Stream<Billing> findAll(Set<BillingId> billingIds);
}
