package freelance.domain.models.entity;

import freelance.domain.annotation.SideEffectOnParameters;
import freelance.domain.exception.DomainException;
import freelance.domain.models.objetValue.EmployeeId;
import freelance.domain.models.objetValue.EmployeeRole;
import freelance.domain.models.objetValue.UserId;
import freelance.domain.security.Auth;
import jakarta.annotation.Nonnull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Employee extends Auditable{
    EmployeeId id;
    UserId userId;
    Set<EmployeeRole> employeeRoles;

    public Employee(@Nonnull EmployeeId employeeId, UserId userId, Set<EmployeeRole> employeeRoles, LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(createdDate, updatedDate);
        this.userId = userId;
        this.id=employeeId;
        this.employeeRoles=employeeRoles;
    }
    @SideEffectOnParameters(ofType = {User.class})
    public Employee(User user,Set<EmployeeRole> employeeRoles) {
        this.userId = user.getId();
        this.employeeRoles=employeeRoles;
        user.addEmployeeProfile(this);
    }
    public void addRole(EmployeeRole role, Auth curent){
        //Il faudra hacker un utilisateur de départ qui sera salary pour ajouter les premiers roles
        if(curent.hasNoneOfRoles(EmployeeRole.ADMIN)){
            throw new DomainException(" only admin can add role ");
        }
        this.addRole(role);
    }
    public void removeRole(EmployeeRole role,Auth auth){
        if(auth.hasNoneOfRoles(EmployeeRole.ADMIN)){
            throw new DomainException(" only admin can add role ");
        }
        remove(role);
    }
    protected void addRole(EmployeeRole role){
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
        return employeeRoles;
    }
}
