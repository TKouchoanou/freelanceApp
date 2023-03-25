package domain.model;

import freelance.domain.models.entity.*;
import freelance.domain.models.objetValue.*;
import freelance.domain.security.Auth;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZModelUtils {

  public   static Freelance createFreelance(){
     return    new Freelance(createUser(), createRib(), createCompany("tkb"));
    }
public     static Freelance createFreelanceWithPersistedRib(){
        return   new Freelance(createUser(), createRibPersisted(1L), createCompany("tkb"));
    }

      public static Freelance createFreelanceWithPersistedRib(Long id){
        return   new Freelance(createUser(), createRibPersisted(id), createCompany(id,"tk"+id,createRibPersisted(id)));
    }

  public   static Freelance peristedFreeLance(Long id){
        Freelance freelance= createFreelanceWithPersistedRib();
        FreelanceId freelanceId=new FreelanceId(id);
        return new Freelance(freelanceId,freelance.getUserId(),freelance.getRibId(),freelance.getCompanyId(),freelance.getCreatedDate(),freelance.getUpdatedDate());
    }
     static User createUser(){
        return new User(new UserId(5L),"firsName","lastName",new Email("email.ts@yahoo.fr"),new Password("password"), Set.of(Profile.SALARY),true, LocalDateTime.now(),LocalDateTime.now());
    }
  public   static User createUser(Long id){
        return new User(new UserId(id),"firsName","lastName",new Email("email.ts@yahoo.fr"),new Password("password"), Set.of(Profile.SALARY),true, LocalDateTime.now(),LocalDateTime.now());
    }

   public static Employee createPersistEmployee(User user){
        return new Employee(new EmployeeId(5L),user.getId(), new HashSet<>(), LocalDateTime.now(),LocalDateTime.now());
    }
  public   static Auth createAuth(EmployeeRole ...employeeRoles){
        //UserId userId,@Nullable EmployeeId employeeId,@Nullable FreelanceId freelanceId, @Nullable RibId ribId, String firstName, String lastName,
        //Email email, Set<Profile> profiles, Set<EmployeeRole> employeeRoles
        return new Auth(new UserId(4L),new EmployeeId(1L),new FreelanceId(1L),new RibId(1L),"firsName","lastName",new Email("email.tk@gmail.fr"),new HashSet<>(), Arrays.stream(employeeRoles).collect(Collectors.toSet()));
    }
  public   static Auth createAdminAuth(){
        return createAuth(EmployeeRole.ADMIN);
    }
   public static Auth createSimpleAuth(){
        return createAuth();
    }

   public static Auth createHumanRessource(){
        return createAuth(EmployeeRole.HUMAN_RESOURCE);
    }
  public   static Auth createAuth(User user,EmployeeRole ...employeeRoles){
        //UserId userId,@Nullable EmployeeId employeeId,@Nullable FreelanceId freelanceId, @Nullable RibId ribId, String firstName, String lastName,
        //Email email, Set<Profile> profiles, Set<EmployeeRole> employeeRoles
        return new Auth(user.getId(),new EmployeeId(1L),new FreelanceId(1L),new RibId(1L),"firsName","lastName",new Email("email.tk@gmail.fr"),new HashSet<>(), new HashSet<>(Arrays.stream(employeeRoles).toList()));
    }
  public   static Rib createRib(){
         return  new Rib("username","FR7628521966142334508845009","ABCDEFGH","09");
    }
   public static Rib createRibPersisted(Long id){
        return  new Rib(new RibId(id),"username",new Iban("FR7628521966142334508845009"),new Bic("ABCDEFGH"),new CleRib("09"));
    }
  public   static Rib createRibPersisted(Long id, String iban){
        return  new Rib(new RibId(id),"username",new Iban(iban),new Bic("ABCDEFGH"),new CleRib("09"));
    }

  public   static Rib createRib(String iban){
        return  new Rib("username",iban,"ABCDEFGH","09");
    }
  public   static Company createCompany(String name){
         return  new Company(createRib(),name);
    }
    public   static Company createCompany(Long id, String name){
        return  new Company(createRibPersisted(id),name);
    }
  public   static Company createCompany(String name,Rib rib){
        return  new Company(rib,name);
    }
  public   static Company createCompany(Long v, String name,Rib rib){
        return  new Company(new CompanyId(v),name,rib.getId(),new HashSet<>(), new HashSet<>(),LocalDateTime.now(),LocalDateTime.now());
    }


}
