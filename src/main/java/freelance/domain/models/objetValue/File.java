package freelance.domain.models.objetValue;

import java.util.UUID;

public record File(UUID Id, byte[] file, String filename) {
}
