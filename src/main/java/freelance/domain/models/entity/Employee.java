package freelance.domain.models.entity;

import freelance.domain.models.objetValue.EmployeeId;
import freelance.domain.models.objetValue.EmployeeRole;
import freelance.domain.models.objetValue.UserId;

import java.time.LocalDateTime;
import java.util.*;

public class Employee extends Auditable{
    EmployeeId id;
    UserId userId;
   Set<EmployeeRole> employeeRoles;

    public Employee(EmployeeId employeeId, UserId userId, Set<EmployeeRole> employeeRoles,LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(createdDate, updatedDate);
        this.userId = userId;
        this.id=employeeId;
        this.employeeRoles = employeeRoles;
    }

    public Employee(UserId userId, Set<EmployeeRole> employeeRoles) {
        this.userId = userId;
        this.employeeRoles = employeeRoles;
    }
    public void addRole(EmployeeRole role){
        if(employeeRoles==null){
            employeeRoles= new HashSet<>();
        }
        employeeRoles.add(role);
    }
    public void remove(EmployeeRole role){
        if(employeeRoles!=null){
            employeeRoles.remove(role);
        }
    }
    public boolean hasRole(EmployeeRole role){
        if(employeeRoles!=null){
         return employeeRoles.contains(role);
        }
        return false;
    }

    public  boolean isThisUser(User user){
        return this.userId==user.getId();

    }
    public Set<EmployeeRole> getEmployeeRoles() {
        return Collections.unmodifiableSet(employeeRoles);
    }
}
