package freelance.service.utils;

import freelance.domain.models.objetValue.EmployeeRole;
import freelance.domain.models.objetValue.Profile;

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
}
