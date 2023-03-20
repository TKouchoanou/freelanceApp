package freelance.service.command.utils;

import freelance.domain.models.objetValue.*;
import freelance.domain.security.Auth;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthProviderImpl implements AuthProvider {

   Auth auth;
    @Override
    public Auth getCurrentAuth() {
        return auth!=null?auth: new Auth(new UserId(1L),new EmployeeId(1L),null,new RibId(1L),"Marc","Arthur",
             new Email("ptitkouche@yahoo.fr"), Set.of(Profile.SALARY),Set.of(EmployeeRole.ADMIN));
    }
    @Override
    public void setCurrentAuth(Auth auth) {
       this.auth =auth;
    }
}
