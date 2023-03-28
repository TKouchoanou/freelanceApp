package freelance.domain.output.repository;

import freelance.domain.core.entity.Rib;
import freelance.domain.core.objetValue.RibId;

public interface RibRepository extends CrudRepository<Rib,RibId> {
    default String getEntityName() {
        return "Rib";
    }
}
