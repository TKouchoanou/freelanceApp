package freelance.application.command.utils;

import freelance.domain.common.security.Auth;

public interface AuthProvider {
    Auth getCurrentAuth();

    void setCurrentAuth(Auth auth);
}
