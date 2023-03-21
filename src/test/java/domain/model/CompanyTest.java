package domain.model;

import freelance.domain.exception.DomainException;
import freelance.domain.models.entity.Company;
import freelance.domain.models.entity.Freelance;
import freelance.domain.models.entity.Rib;
import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.EmployeeRole;
import freelance.domain.security.Auth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class CompanyTest {
    private Auth admin;
    private Auth simpleAuth;
    private Auth humanResource;

    private Auth financial;
    private User user;
    private User newUser;
    private Rib rib1;
    private Rib rib2;
    private Company tKcompany;

    Freelance freelance;
    @BeforeEach
    void setUp() {
        admin = ZModelUtils.createAdminAuth();
        simpleAuth = ZModelUtils.createSimpleAuth();
        humanResource = ZModelUtils.createHumanRessource();
        financial =ZModelUtils.createAuth(EmployeeRole.FINANCE);
        rib1 = ZModelUtils.createRibPersisted(1L);
        rib2 = ZModelUtils.createRibPersisted(2L,"FR1420041010050500013M02606");
        tKcompany = ZModelUtils.createCompany("TK", rib1);
        freelance=ZModelUtils.peristedFreeLance(30L);
    }
    @Test
    public void testChangedWithSimpleAuth(){
        Set<Freelance> freelanceSet= new HashSet<>();
        freelanceSet.add(ZModelUtils.createFreelanceWithPersistedRib());
        freelanceSet.add(freelance);

        Assertions.assertThrows(DomainException.class,()->tKcompany.addFreelance(freelance,simpleAuth));
        Assertions.assertThrows(DomainException.class,()->tKcompany.changeRib(rib2,freelanceSet,simpleAuth));
    }

    @Test
    public void testChangeWithAdmin(){
        Set<Freelance> freelanceSet= new HashSet<>();
        freelanceSet.add(ZModelUtils.peristedFreeLance(45L));
        freelanceSet.add(freelance);
        Assertions.assertDoesNotThrow(()->tKcompany.addFreelance(freelance,admin));
        Assertions.assertDoesNotThrow(()->tKcompany.changeRib(rib2,freelanceSet,admin));
    }
}
