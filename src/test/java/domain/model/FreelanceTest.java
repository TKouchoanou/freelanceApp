package domain.model;

import freelance.domain.common.exception.DomainException;
import freelance.domain.core.entity.Company;
import freelance.domain.core.entity.Freelance;
import freelance.domain.core.entity.Rib;
import freelance.domain.core.entity.User;
import freelance.domain.core.objetValue.Profile;
import freelance.domain.common.security.Auth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FreelanceTest {
    private Auth admin;
    private Auth simpleAuth;
    private Auth humanResource;
    private User user;
    private Rib rib1;
    private Rib rib2;
    private Company tKcompany;
    private Company tKSMcompany;
    private Freelance freelance;

    @BeforeEach
    void setUp() {
        admin = ZModelUtils.createAdminAuth();
        simpleAuth = ZModelUtils.createSimpleAuth();
        humanResource = ZModelUtils.createHumanRessource();
        user = ZModelUtils.createUser();
        rib1 = ZModelUtils.createRibPersisted(1L);
        rib2 = ZModelUtils.createRibPersisted(2L,"FR1420041010050500013M02606");
        tKcompany = ZModelUtils.createCompany("TK", rib1);
        tKSMcompany = ZModelUtils.createCompany("TKSM", rib2);

    }

    @Test()
    void testCreateEmployeeWithSuccess() {
        Assertions.assertFalse(user.hasProfile(Profile.FREELANCE));
        freelance = new Freelance(user, rib1, tKcompany);
        Assertions.assertTrue(freelance.hasCompanyRib(tKcompany));
        Assertions.assertFalse(freelance.hasCompanyRib(tKSMcompany));
        Assertions.assertTrue(user.hasProfile(Profile.FREELANCE));
    }

    @Test
    void testChangeCompanyAndRibWithSimpleAuth() {
        freelance = new Freelance(user, rib1, tKcompany);
        Assertions.assertThrows(DomainException.class, () -> freelance.changeCompany(tKcompany, tKSMcompany, simpleAuth));
        Assertions.assertThrows(DomainException.class, () -> freelance.changeRib(rib2, simpleAuth));
    }

    @Test
    void testChangeCompanyAndRibWithAdminAuth() {
        freelance = new Freelance(user, rib1, tKcompany);
        Assertions.assertDoesNotThrow(() -> freelance.changeCompany(tKcompany, tKSMcompany, admin));
        Assertions.assertDoesNotThrow(() -> freelance.changeRib(rib2, admin));
        Assertions.assertEquals(rib2.getId(),freelance.getRibId());
        Assertions.assertEquals(freelance.getCompanyId(),tKSMcompany.getId());
    }

    @Test
    void testChangeCompanyAndRibWithHumanResourceAuth() {
        freelance = new Freelance(user, rib1, tKcompany);
        Assertions.assertDoesNotThrow(() -> freelance.changeCompany(tKSMcompany, tKcompany, humanResource));
        Assertions.assertDoesNotThrow(() -> freelance.changeRib(rib1, humanResource));
        Assertions.assertEquals(rib1.getId(),freelance.getRibId());
        Assertions.assertEquals(freelance.getCompanyId(),tKcompany.getId());
    }

}
