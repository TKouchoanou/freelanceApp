package freelance.domain.models.entity;

import freelance.domain.exception.DomainException;
import freelance.domain.models.objetValue.EmployeeId;
import freelance.domain.models.objetValue.EmployeeRole;
import freelance.domain.models.objetValue.Profile;
import freelance.domain.models.objetValue.UserId;
import freelance.domain.security.Auth;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Employee extends Auditable{
    EmployeeId id;
    UserId userId;
    Set<EmployeeRole> employeeRoles;

    public Employee(EmployeeId employeeId, UserId userId,LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(createdDate, updatedDate);
        this.userId = userId;
        this.id=employeeId;
    }
    public Employee(UserId userId) {
        this.userId = userId;
    }
    public void addRole(EmployeeRole role, Auth curent){
        //Il faudra hacker un utilisateur de départ qui sera salary pour ajouter les premiers roles
        if(curent.hasNoneOfRoles(EmployeeRole.ADMIN)){
            throw new DomainException(" only admin can add role ");
        }
        this.addRole(role);
    }
    private void removeRole(EmployeeRole role,Auth auth){
        if(auth.hasNoneOfRoles(EmployeeRole.ADMIN)){
            throw new DomainException(" only admin can add role ");
        }
        remove(role);
    }
    private void addRole(EmployeeRole role){
        //Il faudra hacker un utilisateur de départ qui sera salary pour ajouter les premiers roles
        if(employeeRoles==null){
            employeeRoles= new HashSet<>();
        }
        employeeRoles.add(role);
    }
    private void remove(EmployeeRole role){
        if(employeeRoles!=null){
            employeeRoles.remove(role);
        }
    }

    public  boolean isThisUser(User user){
        return this.userId==user.getId();
    }

    public EmployeeId getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public Set<EmployeeRole> getEmployeeRoles() {
        return Collections.unmodifiableSet(employeeRoles);
    }
}
