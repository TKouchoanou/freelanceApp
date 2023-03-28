package freelance.application.query.annotation;

import freelance.domain.core.objetValue.EmployeeRole;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RolesAllowed {
    EmployeeRole[] value();
    boolean allowOwner() default false;
}
