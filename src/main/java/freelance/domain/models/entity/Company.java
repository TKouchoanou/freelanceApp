package freelance.domain.models.entity;

import freelance.domain.models.objetValue.CompanyId;

import java.time.LocalDateTime;

public class Company extends Auditable {
    CompanyId id;
    String name;
}
