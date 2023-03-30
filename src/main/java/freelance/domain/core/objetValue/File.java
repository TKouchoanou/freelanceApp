package freelance.domain.core.objetValue;

import java.util.UUID;

public record File(UUID id, byte[] file, String context) {
}
