package freelance.service.command.utils;

import freelance.domain.repository.AuthRepository;
import freelance.domain.security.Auth;
import org.springframework.stereotype.Component;

@Component
public class AuthProviderImpl implements AuthProvider {

    AuthRepository authRepository;
    AuthProviderImpl(AuthRepository authRepository){
        this.authRepository=authRepository;
    }

    AuthProviderImpl(){
    }
    @Override
    public Auth getCurrentAuth() {
        return new Auth();
    }
}
