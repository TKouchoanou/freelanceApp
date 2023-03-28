package freelance.domain.output.repository;

import freelance.domain.core.objetValue.Email;
import freelance.domain.common.security.Auth;

import java.util.Optional;

public interface AuthRepository {
    Optional<Auth> findByEmail(Email email);
}
