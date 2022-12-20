package freelance.domain.repository;

import freelance.domain.models.objetValue.Email;
import freelance.domain.security.Auth;

import java.util.Optional;

public interface AuthRepository {
    Optional<Auth> findByEmail(Email email);
}
