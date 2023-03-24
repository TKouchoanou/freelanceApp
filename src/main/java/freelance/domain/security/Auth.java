package freelance.domain.security;

import freelance.domain.models.entity.*;
import freelance.domain.models.objetValue.*;
import jakarta.annotation.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//must be moved in use case if we decided to implement authorisatison rule in use case
public final class Auth {
 // le current user est un Auth et un Auth est unique dans l'application
 // imaginer qu'un use case travail sur les users, si le currentUser est un User il peut passer n'importe quel user qui se ferra passer pour le current user
 // De plus les methodes dont on a besoin sur le current user ne sont pas les même que celle sur un user destinée à persister les entités users
 final UserId userId;
 final EmployeeId employeeId;
 FreelanceId freelanceId;
 CompanyId companyId;
 RibId companyRibId;
 RibId ribId;
 String firstName;
 String lastName;
 Email email;
 Set<Profile> profiles;
 Set<EmployeeRole> employeeRoles;
 public Auth(UserId userId,@Nullable EmployeeId employeeId,@Nullable FreelanceId freelanceId, @Nullable RibId ribId, String firstName, String lastName,
             Email email, Set<Profile> profiles, Set<EmployeeRole> employeeRoles){
  this.userId=userId; this.employeeId=employeeId; this.freelanceId=freelanceId; this.firstName=firstName;
  this.lastName=lastName;this.email=email; this.profiles=profiles; this.employeeRoles=employeeRoles;
  this.ribId=ribId;
 }
 boolean isActive;

 public  boolean isThisUser(User user){
  return this.userId==user.getId();
 }
 public boolean hasALeastOneOfRoles(Set<EmployeeRole> roles){
  return this.employeeRoles.stream().anyMatch(roles::contains);
 }
 public boolean hasALeastOneOfRoles(EmployeeRole ...roles){
  return this.hasALeastOneOfRoles(Arrays.stream(roles).collect(Collectors.toSet()));
 }

 public boolean hasNoneOfRoles(Set<EmployeeRole> roles){
  return !this.hasALeastOneOfRoles(roles);
 }
 public boolean hasNoneOfRoles(EmployeeRole ...roles){
  return this.hasNoneOfRoles(Arrays.stream(roles).collect(Collectors.toSet()));
 }

 public Email getEmail() {
  return email;
 }

 public boolean isOwner(Billing billing){
 return Optional.ofNullable(billing)
         .map(Billing::getUserId)
         .map(userId1 -> userId==userId1)
         .orElse(false);
}
 public boolean isOwner(Rib rib){
  return rib.getId()!=null && (rib.getId().equals(this.ribId) || rib.getId().equals(companyRibId)) ;
 }
 public boolean isOwner(Object object){
  if(object instanceof  Rib){
   return this.isOwner((Rib) object);
  }
  if(object instanceof  RibId){
   return object.equals(this.ribId) || object.equals(companyRibId);
  }

  if(object instanceof  Billing){
    return this.isOwner((Billing) object);
  }

  if(object instanceof Freelance){
   return this.freelanceId!=null &&
           this.freelanceId.equals(((Freelance) object).getId());
  }
  if(object instanceof FreelanceId){
   return this.freelanceId!=null && this.freelanceId.equals(object);
  }

  if(object instanceof Employee){
   return this.employeeId!=null &&
           this.employeeId.equals(((Employee) object).getId());
  }
  if(object instanceof EmployeeId){
   return this.employeeId!=null &&
           this.employeeId.equals(object);
  }
  if(object instanceof UserId){
   return this.userId!=null &&
           this.userId.equals(object);
  }

  if(object instanceof User){
   return this.userId!=null &&
           this.userId.equals(((User) object).getId());
  }

  if(object instanceof Collection<?>) {

   if (!((Collection<?>) object).isEmpty()) {
    for (Object value : (Collection<?>) object) {
     if (!this.isOwner(value)) {
      return false;
     }
    }
    return true;
   }
  }

   if(object instanceof Stream<?>){
    Collection<?> collection=((Stream<?>) object).toList();
    return this.isOwner(collection);
   }

  return false;
 }





}
