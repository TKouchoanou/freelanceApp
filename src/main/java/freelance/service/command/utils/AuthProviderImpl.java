package freelance.service.command.utils;

import freelance.domain.models.objetValue.*;
import freelance.domain.repository.AuthRepository;
import freelance.domain.security.Auth;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

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
        return new Auth(new UserId(1L),new EmployeeId(1L),null,new RibId(1L),"Marc","Arthur",
             new Email("ptitkouche@yahoo.fr"), Set.of(Profile.SALARY),Set.of(EmployeeRole.ADMIN));
    }
}
