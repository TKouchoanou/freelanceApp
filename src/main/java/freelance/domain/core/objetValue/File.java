package freelance.domain.core.objetValue;

import java.util.UUID;

public record File(UUID Id, byte[] file, String filename) {
}
