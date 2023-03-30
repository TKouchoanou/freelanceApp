package freelance.application.utils;

import freelance.domain.core.objetValue.EmployeeRole;
import freelance.domain.core.objetValue.PaymentStatus;
import freelance.domain.core.objetValue.Profile;
import freelance.domain.core.objetValue.ValidationStatus;

import java.util.Arrays;
import java.util.stream.Stream;

public interface DomainEnumMapper {
    //TODO move some object values that will be needed in the presentation layer in a separate module like common-domain-core.
    // thus the presentation layer will have it in dependency, without needing to go through here since the presentation layer must not know the domain layer but only the layer which is adjacent to it (the service layer)
    // I have to valid if thus does not violate DDD or hexagonal rule
  public static Stream<String> profiles(){
       return Arrays.stream(Profile.values()).map(Profile::name);
    }

   public static Stream<String> employeeRoles(){
        return Arrays.stream(EmployeeRole.values()).map(EmployeeRole::name);
    }
    static Stream<String> billingPaymentStatus(){
        return Arrays.stream(PaymentStatus.values()).map(PaymentStatus::name);
    }
    static Stream<String> billingValidationStatus(){
        return Arrays.stream(ValidationStatus.values()).map(ValidationStatus::name);
    }
}
