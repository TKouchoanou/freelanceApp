package freelance.domain.security;

import freelance.domain.exception.DomainException;
import freelance.domain.models.entity.Billing;
import freelance.domain.models.entity.Freelance;
import freelance.domain.models.entity.Rib;
import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.*;

import java.util.*;
import java.util.stream.Collectors;
//must be moved in use case if we decided to implement authorisatison rule in use case
public final class Auth {
 // le current user est un Auth et un Auth est unique dans l'application
 // imaginer qu'un use case travail sur les users, si le currentUser est un User il peut passer n'importe quel user qui se ferra passer pour le current user
 // De plus les methodes dont on a besoin sur le current user ne sont pas les même que celle sur un user destinée à persister les entités users
 UserId userId;
 EmployeeId employeeId;
 FreelanceId freelanceId;
 String firstName;
 String lastName;
 Email email;
 Password passWord;
 Set<Profile> profiles;
 Set<EmployeeRole> employeeRoles;

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

public boolean isOwner(Billing billing){
 return Optional.ofNullable(billing)
         .map(Billing::getUserId)
         .map(userId1 -> userId==userId1)
         .orElse(false);
}









/*

*/





}
