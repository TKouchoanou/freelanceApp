package freelance.domain.models.objetValue;

import freelance.domain.exception.DomainException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public record Email (String value){
    public Email {
        if (!EmailValidator.getInstance().isValid(value)) {
            throw new DomainException("Invalid email");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email)) return false;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
