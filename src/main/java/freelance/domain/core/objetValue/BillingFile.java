package freelance.domain.core.objetValue;

import java.util.UUID;

public record BillingFile(UUID fileId,String context, String originalName) {
}
