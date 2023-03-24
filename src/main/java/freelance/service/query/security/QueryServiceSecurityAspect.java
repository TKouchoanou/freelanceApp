package freelance.service.query.security;

import freelance.domain.models.objetValue.EmployeeId;
import freelance.domain.models.objetValue.EmployeeRole;
import freelance.domain.models.objetValue.FreelanceId;
import freelance.domain.models.objetValue.UserId;
import freelance.domain.security.Auth;
import freelance.service.command.utils.AuthProvider;
import freelance.service.query.annotation.RolesAllowed;
import freelance.service.query.model.*;
import freelance.service.utils.QueryToDomainMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

interface QueryServiceAccessControlInterceptor {
    @SuppressWarnings({"unused"})
    void checkRoles(RolesAllowed annotation,Object result);
}
@Aspect
@Component
@SuppressWarnings({"unused"})
public class QueryServiceSecurityAspect implements QueryServiceAccessControlInterceptor {
    AuthProvider authProvider;
    QueryServiceSecurityAspect(AuthProvider authProvider){
        this.authProvider=authProvider;
    }
    @Around("execution(* freelance.service.query.UserQueryService.*(..))")
    Object intercept(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature=(MethodSignature) pjp.getSignature();
        Method method= signature.getMethod();
        Method interfaceMethod=getInterfaceMethod(method);
        RolesAllowed rolesAllowedAnnotation= interfaceMethod.getAnnotation(RolesAllowed.class);
        Object result=pjp.proceed();
       if(rolesAllowedAnnotation!=null){
         this.checkRoles(rolesAllowedAnnotation,result);
       }
       return result;
    }

    @Override
    public void checkRoles(RolesAllowed annotation,Object result) {
        Auth auth = authProvider.getCurrentAuth();
        if (auth == null) {
            throw new QuerySecurityException("Access is denied");
        }

        EmployeeRole[] allowedRoles = annotation.value();

        if (allowedRoles.length == 0) {
            throw new QuerySecurityException("No roles specified for method");
        }

        boolean isAuthorized = auth.hasALeastOneOfRoles(allowedRoles);

        if(!isAuthorized && annotation.allowOwner()) {
            isAuthorized = isOwner(auth,result);
        }

        if (!isAuthorized) {
            throw new QuerySecurityException("Access is denied");
        }
    }


    private Method getInterfaceMethod(Method method){
        Type[] interfaces= method.getDeclaringClass().getGenericInterfaces();
         Method[] iMethods=   Arrays.stream(interfaces)
                .map($interface->((Class<?>) $interface).getDeclaredMethods())
                .filter(methods-> contains(methods, method))
                .findAny()
                .orElse(new Method[0]) ;

        for (Method iM: iMethods) {
            if(equals(iM,method)){
                return iM;
            }
        }
        return method;
    }


     private boolean contains(Method[] methods, Method search){
         for (Method method: methods) {
             if(equals(method,search)){
                 return true;
             }
       }
       return false;
    }
    private boolean equals(Method method1, Method method2) {
            if (method1.getName().equals(method2.getName())) {
                if (!method1.getReturnType().equals(method2.getReturnType()))
                    return false;
                return Arrays.equals(method1.getParameterTypes(), method2.getParameterTypes());
            }

        return false;
    }

    private boolean isOwner(Auth auth, Object ressource){
        if(ressource instanceof Rib){
          return auth.isOwner(QueryToDomainMapper.map((Rib)ressource));
        }

        if(ressource instanceof Billing){
            return auth.isOwner(QueryToDomainMapper.map((Billing)ressource));
        }
        if(ressource instanceof User){
            return auth.isOwner(new UserId(((User) ressource).getId()));
        }

        if(ressource instanceof Employee){
            return auth.isOwner(new EmployeeId(((Employee) ressource).getId()));
        }

        if(ressource instanceof Freelance){
            return auth.isOwner(new FreelanceId(((Freelance) ressource).getId()));
        }
        if(ressource instanceof Stream<?>){
            Collection<?> collection=((Stream<?>) ressource).toList();
            return this.isOwner(auth,collection);
        }
        if(ressource instanceof Collection<?>){
            for (Object value: (Collection<?>) ressource) {
                if(!this.isOwner(auth,value)){
                    return false;
                }
            }
        }
        return false;
    }


}
