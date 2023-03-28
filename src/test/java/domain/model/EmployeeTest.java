package domain.model;

import freelance.domain.common.exception.DomainException;
import freelance.domain.core.entity.Employee;
import freelance.domain.core.entity.User;
import freelance.domain.core.objetValue.*;
import freelance.domain.common.security.Auth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class EmployeeTest {
    @Test
    public void createEmployeeWithSuccess(){

        User user = ZModelUtils.createUser();
        // test constructor that repository will use to populate entity with database data
        Assertions.assertDoesNotThrow(()->new Employee(new EmployeeId(5L),user.getId(), Set.of(EmployeeRole.HUMAN_RESOURCE), LocalDateTime.now(),LocalDateTime.now()));

        Set<EmployeeRole> employeeRoles=new HashSet<>();
        employeeRoles.add(EmployeeRole.HUMAN_RESOURCE);
        Employee employee=new Employee(user, employeeRoles);

        // test created date et updateDate are set
        Assertions.assertNotEquals(null,employee.getCreatedDate());
        Assertions.assertNotEquals(null,employee.getUpdatedDate());
    }
    @Test
    public void addAndRemoveRoleEmployeeWithSuccess(){
        User user = ZModelUtils.createUser();

        Set<EmployeeRole> employeeRoles=new HashSet<>();
        employeeRoles.add(EmployeeRole.HUMAN_RESOURCE);
        Employee employee=new Employee(user, employeeRoles);

        // test to add role with user who is not admin
        Auth simpleAuth=ZModelUtils.createAuth();
        Assertions.assertThrows(DomainException.class,()->employee.addRole(EmployeeRole.ADMIN,simpleAuth));
        Assertions.assertThrows(DomainException.class,()->employee.removeRole(EmployeeRole.ADMIN,simpleAuth));

        // test to add and role with user who is admin
        Auth adminAuth=ZModelUtils.createAuth(EmployeeRole.ADMIN);
        Assertions.assertFalse(employee.getEmployeeRoles().contains(EmployeeRole.ADMIN));
        Assertions.assertDoesNotThrow(()->employee.addRole(EmployeeRole.ADMIN,adminAuth));
        Assertions.assertTrue(employee.getEmployeeRoles().contains(EmployeeRole.ADMIN));
        Assertions.assertDoesNotThrow(()->employee.removeRole(EmployeeRole.ADMIN,adminAuth));
        Assertions.assertFalse(employee.getEmployeeRoles().contains(EmployeeRole.ADMIN));

    }

}
