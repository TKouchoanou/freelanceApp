package freelance.domain.annotation;

import freelance.domain.models.entity.Freelance;

public @interface SideEffectOnParameters {

    Class[] ofType();
}
