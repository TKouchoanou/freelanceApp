package domain.model;

import freelance.domain.common.exception.DomainException;
import freelance.domain.core.entity.*;
import freelance.domain.core.objetValue.*;
import freelance.domain.common.security.Auth;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillingTest {
    private Auth admin;
    private Auth simpleAuth;
    private Auth humanResource;

    private Auth financial;
    private User user;
    private User newUser;
    private Rib rib1;
    private Rib rib2;
    private Company tKcompany;
    private Company tKSMcompany;
    private Freelance freelance;
    private Period period;
    private BillingFile billingFile;
    private Money ht;
    private Money ttc;


    @BeforeEach
    void setUp() {
        admin = ZModelUtils.createAdminAuth();
        simpleAuth = ZModelUtils.createSimpleAuth();
        humanResource = ZModelUtils.createHumanRessource();
        financial =ZModelUtils.createAuth(EmployeeRole.FINANCE);
        user = ZModelUtils.createUser(1L);
        newUser=ZModelUtils.createUser(2L);
        rib1 = ZModelUtils.createRibPersisted(1L);
        rib2 = ZModelUtils.createRibPersisted(2L,"FR1420041010050500013M02606");
        tKcompany = ZModelUtils.createCompany("TK", rib1);
        tKSMcompany = ZModelUtils.createCompany("TKSM", rib2);
        period= new Period(LocalDate.now(),LocalDate.now().plusDays(3));
        billingFile= new BillingFile(new byte[2],"file1");
        ht=Money.of(new BigDecimal(1050),"EUR");
        ttc=Money.of(new BigDecimal(1000),"EUR");
    }

   @Test
    public void testChangePeriodWithSimpleAuth(){
       Period newPeriod=new Period(LocalDate.now().minusMonths(1),LocalDate.now().plusWeeks(1));
       Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
       Assertions.assertThrows(DomainException.class,()->billing.changePeriod(newPeriod,simpleAuth));
    }

    @Test
    public void testUpdateAmountWithSimpleAuth(){
        Money nextTTC=ttc.multiply(2);
        Money nexHT=ht.multiply(2);
        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        Assertions.assertThrows(DomainException.class,()->billing.updateAmount(nextTTC,nexHT,simpleAuth));
    }
    @Test
    public void testUpdateAmountWithFinancial(){
        Money nextTTC=ttc.multiply(2);
        Money nexHT=ht.multiply(2);
        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        Assertions.assertThrows(DomainException.class,()->billing.updateAmount(nextTTC,nexHT,simpleAuth));
    }
    @Test
    public void testChangeUserWithSimpleAuth(){
        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        Assertions.assertThrows(DomainException.class,()->billing.changeUser(user,newUser,simpleAuth));
    }
    @Test
    public void testChangeUserWithFinanceEmployee(){
        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        Assertions.assertThrows(DomainException.class,()->billing.changeUser(user,newUser,financial));
    }

    @Test
    public void testChangeCompanyWithSimpleAuth(){
        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        Assertions.assertThrows(DomainException.class,()->billing.changeCompany(tKSMcompany,simpleAuth));
    }

    @Test
    public void testChangeFileWithSimpleAuth(){
        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        Assertions.assertThrows(DomainException.class,
                ()->billing.changeFile(new BillingFile(new byte[2],"file"),simpleAuth));
    }

    @Test
    public void testChangeValidationStatusWithFinancial(){
        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        Assertions.assertThrows(DomainException.class,
                ()->billing.updateValidateStatus(ValidationStatus.VALIDATE,financial));
    }
    @Test
    public void testChangeValidationStatusWithHumanResource(){
        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        Assertions.assertDoesNotThrow(()->billing.updateValidateStatus(ValidationStatus.VALIDATE,humanResource));
    }

    @Test
    public void testChangePaymentStatusWithHumanResource(){
        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        Assertions.assertThrows(DomainException.class, ()->billing.updatePayementStatus(PaymentStatus.PAID,humanResource));
    }
    @Test
    public void testChangePaymentStatusWithFinancial(){
        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        Assertions.assertThrows(DomainException.class,()->billing.updatePayementStatus(PaymentStatus.PAID,financial));
        billing.updateValidateStatus(ValidationStatus.VALIDATE,humanResource);
        Assertions.assertDoesNotThrow(()->billing.updatePayementStatus(PaymentStatus.PAID,financial));
        Assertions.assertEquals(billing.getPaymentStatus(),PaymentStatus.PAID);
    }

    @Test
    public void testChangePeriodAdminAuth(){
        Period newPeriod=new Period(LocalDate.now().minusMonths(1),LocalDate.now().plusWeeks(1));
        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        Assertions.assertDoesNotThrow(()->billing.changePeriod(newPeriod,admin));
        Assertions.assertEquals(newPeriod,billing.getPeriod());
    }

}
