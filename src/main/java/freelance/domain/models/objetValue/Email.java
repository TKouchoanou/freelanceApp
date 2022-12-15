package freelance.domain.models.objetValue;

import freelance.domain.exception.DomainException;
import org.apache.commons.validator.routines.EmailValidator;

public record Email (String value){
    public Email {
        if (!EmailValidator.getInstance().isValid(value)) {
            throw new DomainException("Invalid email");
        }
    }
}
