package freelance.domain.models.entity;

import java.time.LocalDateTime;

public abstract class Auditable {
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
}
