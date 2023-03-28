package domain.model;

import freelance.domain.core.entity.*;
import freelance.domain.core.objetValue.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

public class UserTest {

    @Test
    public void createUserWithSuccess(){
        Set<EmployeeRole> roles=Set.of(EmployeeRole.ADMIN);

        Assertions.assertDoesNotThrow(this::createNewUser);
        User newUser = this.createNewUser();
        Assertions.assertFalse(newUser.hasProfile(Profile.SALARY));
        Employee employee = new Employee(newUser,roles);
        Assertions.assertTrue(newUser.hasProfile(Profile.SALARY));
        Assertions.assertEquals(employee.getEmployeeRoles(), roles);


    }
    @Test
    public void createPersistUserWithSuccess(){

        Assertions.assertDoesNotThrow(()->createPersistUser());
        User persistUser=this.createPersistUser();
        Assertions.assertFalse(persistUser.hasProfile(Profile.FREELANCE));
        Freelance freelance = new Freelance(persistUser,createRib(),createCompany());
        Assertions.assertTrue(persistUser.hasProfile(Profile.FREELANCE));



    }
    private User createPersistUser(Profile ...profiles){
        return new User(new UserId(5L),"firsName","lastName",new Email("email.ts@yahoo.fr"),new Password("password"),Set.of(profiles),true, LocalDateTime.now(),LocalDateTime.now());
    }
    private User createNewUser(){
        return new User("firsName","lastName","email.ts@yahoo.fr","password",true);
    }
    public Rib createRib(){
     return new Rib("username","FR7628521966142334508845009","ABCDEFGH","09");
    }
    public Company createCompany(){
        return new Company(createRib(),"TK Buisness");
    }
}
