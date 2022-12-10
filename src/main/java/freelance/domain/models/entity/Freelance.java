package freelance.domain.models.entity;

import freelance.domain.models.objetValue.RibId;
import freelance.domain.models.objetValue.UserId;

import java.time.LocalDateTime;

public class Freelance extends Auditable {
    UserId userId;
    RibId ribId;
}
