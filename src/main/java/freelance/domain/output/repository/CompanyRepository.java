package freelance.domain.output.repository;

import freelance.domain.core.entity.Company;
import freelance.domain.core.objetValue.CompanyId;

public interface CompanyRepository extends CrudRepository<Company, CompanyId>{
    default String getEntityName() {
        return "Company";
    }
}
