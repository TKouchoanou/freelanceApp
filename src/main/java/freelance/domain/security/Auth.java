package freelance.domain.security;

import freelance.domain.models.entity.Billing;
import freelance.domain.models.entity.Rib;
import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.*;
import jakarta.annotation.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
//must be moved in use case if we decided to implement authorisatison rule in use case
public final class Auth {
 // le current user est un Auth et un Auth est unique dans l'application
 // imaginer qu'un use case travail sur les users, si le currentUser est un User il peut passer n'importe quel user qui se ferra passer pour le current user
 // De plus les methodes dont on a besoin sur le current user ne sont pas les même que celle sur un user destinée à persister les entités users
 final UserId userId;
 final EmployeeId employeeId;
 FreelanceId freelanceId;
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

 public boolean hasRole(EmployeeRole role){
  if(employeeRoles!=null){
   return employeeRoles.contains(role);
  }
  return false;
 }
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
 public Set<EmployeeRole> getEmployeeRoles() {
  return Collections.unmodifiableSet(employeeRoles);
 }
 public boolean hasNoneOfRoles(EmployeeRole ...roles){
  return this.hasNoneOfRoles(Arrays.stream(roles).collect(Collectors.toSet()));
 }

 public  boolean hasProfile(Profile profile){
  if(this.profiles!=null){
   return this.profiles.contains(profile);
  }
  return false;
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
  return rib.getId().equals(this.ribId);
 }









 /*

*/





}
