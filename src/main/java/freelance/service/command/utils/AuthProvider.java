package freelance.service.command.utils;

import freelance.domain.security.Auth;

public interface AuthProvider {
    Auth getCurrentAuth();

    void setCurrentAuth(Auth auth);
}
