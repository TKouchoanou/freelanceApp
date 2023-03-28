package freelance.application.command.utils;

import freelance.domain.core.objetValue.*;
import freelance.domain.common.security.Auth;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
//@Scope("prototype")// par session à gérer avec une méthode productrice  annotées par @Bean après chaque conection
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
