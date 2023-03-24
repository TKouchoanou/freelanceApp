package freelance.service.query.annotation;

import freelance.domain.models.objetValue.EmployeeRole;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RolesAllowed {
    EmployeeRole[] value();
    boolean allowOwner() default false;
}
