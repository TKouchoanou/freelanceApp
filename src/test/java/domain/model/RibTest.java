package domain.model;


import freelance.domain.common.exception.DomainException;
import freelance.domain.core.entity.Billing;
import freelance.domain.core.entity.Company;
import freelance.domain.core.entity.Rib;
import freelance.domain.core.entity.User;
import freelance.domain.core.objetValue.*;
import freelance.domain.common.security.Auth;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RibTest {
    private User user;
    private User user2;
    private Rib rib1;
    private Rib rib2;
    private Company tKcompany;
    private Period period;
    private BillingFile billingFile;
    private Money ht;
    private Money ttc;
    private Auth admin;
    private Auth simpleAuth;

    private String _iban;
    private String _iban2;

    private String _bic;
    private String _cleRib;

    private LocalDateTime _date;

    @BeforeEach
    public void SetUp(){
        _iban = "FR7628521966142334508845009";
        _iban2 = "FR1420041010050500013M02606";
        _bic = "BNPAFRPPXXX";
        _cleRib = "09";
        _date=LocalDateTime.now();

        admin = ZModelUtils.createAdminAuth();
        simpleAuth = ZModelUtils.createSimpleAuth();
        rib1 = ZModelUtils.createRibPersisted(1L);
        user = ZModelUtils.createUser(1L);
        user2=ZModelUtils.createUser(2L);
        rib2 = ZModelUtils.createRibPersisted(2L,"FR1420041010050500013M02606");
        tKcompany = ZModelUtils.createCompany("TK", rib1);
        period= new Period(LocalDate.now(),LocalDate.now().plusDays(3));
        billingFile= new BillingFile(new byte[2],"file1");
        ht= Money.of(new BigDecimal(1050),"EUR");
        ttc=Money.of(new BigDecimal(1000),"EUR");
    }


    @Test
    public void createRibWithFailure(){
        Throwable badIban= Assertions.assertThrows(DomainException.class,()-> new Rib("username","28521966142334508845009","ABCDEFGH","09"));
        Throwable badBic= Assertions.assertThrows(DomainException.class,()-> new Rib("username","FR7628521966142334508845009","34CDEFGH5","06"));
        Throwable badCleRib= Assertions.assertThrows(DomainException.class,()-> new Rib("username","FR7628521966142334508845009","ABCDEFGH","0778"));
        Stream.of("invalid","iban").forEach(value->assertTrue(badIban.getMessage().contains(value)));
        Stream.of("invalid","bic").forEach(value->assertTrue(badBic.getMessage().contains(value)));
        Stream.of("invalid","cle", "value").forEach(value->assertTrue(badCleRib.getMessage().contains(value)));
    }
    @Test
    public void testRibWithIdCreation(){
        Iban iban=new Iban(_iban);
        Bic bic=new Bic(_bic);
        CleRib cleRib=new CleRib(_cleRib);

        RibId id1=new RibId(1L);
        RibId id2=new RibId(1L);

        Set<BillingId> billings=billingStream().map(Billing::getId).collect(Collectors.toSet());

        Assertions.assertDoesNotThrow(()-> new Rib(id1,"username",iban,bic,cleRib));
        Assertions.assertDoesNotThrow(()-> new Rib(id2,"username2",iban,bic,cleRib,billings, _date,_date.plusDays(2)));
        Assertions.assertDoesNotThrow(()-> new Rib(id1,"username2",_iban,_bic,_cleRib,billings, _date,_date.plusDays(32)));
    }

    @Test
    public void testAuditableDates(){
        Set<BillingId> billings=billingStream().map(Billing::getId).collect(Collectors.toSet());
        Assertions.assertThrows(DomainException.class,()-> new Rib(new RibId(1L),"username2",_iban,_bic,_cleRib,billings, _date,_date.minusDays(2)));
    }

    @Test
    public void testUpdateIbanWithAdmin(){
        Set<BillingId> billingIds=billingWithIdStream().map(Billing::getId).collect(Collectors.toSet());
        Set<Billing> billings=billingSet(10);
        Rib rib= new Rib(new RibId(1L),"username2",_iban,_bic,_cleRib,billingIds, _date,_date.plusDays(2));
        Assertions.assertDoesNotThrow(()->rib.update(_iban2,billings, admin));
    }
    @Test
    public void testUpdateUserNameIbanCleRib(){
        Set<BillingId> billingIds=billingWithIdStream().map(Billing::getId).collect(Collectors.toSet());
        Rib rib= new Rib(new RibId(1L),"username2",_iban,_bic,_cleRib,billingIds, _date,_date.plusDays(2));
        Assertions.assertThrows(DomainException.class,()->rib.update("autherUserName",_bic,_cleRib, simpleAuth));
        Assertions.assertDoesNotThrow(()->rib.update("autherUserName",_bic,_cleRib, admin));
    }
    @Test
    public void testUpdateIbanWithSimpleAuth(){
        LocalDateTime date=LocalDateTime.now();
        Set<BillingId> billingIds=billingWithIdStream().map(Billing::getId).collect(Collectors.toSet());
        Set<Billing> billings=billingSet(10);
        Rib rib= new Rib(new RibId(1L),"username2",_iban,_bic,_cleRib,billingIds, date,date.plusDays(2));
        Assertions.assertThrows(DomainException.class,()->rib.update(_iban2,billings,simpleAuth ));
    }
   @Test
    public void testUpdateIbanWithBillingWithoutProvidedInParameterAllBillingWhichIsAssociateWithRib(){

        Set<BillingId> billingIds=billingSet(20)
                .stream()
                .map(Billing::getId)
                .collect(Collectors.toSet());
        Set<Billing> billings=billingSet(10);
        Rib rib= new Rib(new RibId(1L),"username2",_iban,_bic,_cleRib,billingIds, _date,_date.plusDays(2));
        Assertions.assertThrows(DomainException.class,()->rib.update(_iban2,billings,admin));
    }

    @Test
    public void testCanUpdateRibWhoHaveAlreadyBeUsedToPaidABilling(){
        Set<BillingId> billingIds=billingSet(5)
                .stream()
                .map(Billing::getId)
                .collect(Collectors.toSet());

        Long maxId=billingIds.stream().map(BillingId::id).max(Long::compareTo).orElse(1L);

        Long  billingWithPAIDStatusId=maxId+1;

        //add billing with PAID status id
        billingIds.add(new BillingId(billingWithPAIDStatusId));
        Set<Billing> billings=billingSet(15);

        ////add billing with PAID status id
        Billing billingWithPAIDStatus=billingWithStatus(maxId+1,PaymentStatus.PAID);
        billings.add(billingWithPAIDStatus);

        Rib rib= new Rib(new RibId(1L),"username2",_iban,_bic,_cleRib,billingIds, _date,_date.plusDays(2));

        Assertions.assertThrows(DomainException.class,()->rib.update(_iban2,billings,admin));
    }
    @Test
    void testRibCreation() {
        String username = "Theo";
        Rib rib = new Rib(username, _iban2, _bic, _cleRib);
        assertEquals(username, rib.getUsername());
        assertEquals(_iban2, rib.getIban().value());
        assertEquals(_bic, rib.getBic().value());
        assertEquals(_cleRib, rib.getCleRib().value());

    }


    @Test
    void testRibUpdate() {
        String username = "Theophane";
        Rib rib = new Rib(username, _iban, _bic, _cleRib);

        Auth auth = ZModelUtils.createAdminAuth();
        rib.update("Jane", "BNPAFRPPYYY", "06", auth);

        assertEquals("Jane", rib.getUsername());
        assertEquals("BNPAFRPPYYY", rib.getBic().value());
        assertEquals("06", rib.getCleRib().value());
    }

    @Test
    void testRibUpdateWithBillings() {
        String username = "Malo";
        Rib rib = new Rib(username, _iban, _bic, _cleRib);
         Set<Billing> billings=billingSet(4);
        rib.update("FR1420041010050500013M02606", billings, admin);
        assertEquals("FR1420041010050500013M02606", rib.getIban().value());
    }

    @Test
    void testRibAddBilling() {
        String username = "John";
        Rib rib = new Rib(username, _iban, _bic, _cleRib);
        Set<Billing> billings=billingSet(4,rib);
        for (Billing billing:billings){
            rib.addBilling(billing);
        }
       Set<BillingId>  billingIds= billings.stream().map(Billing::getId).collect(Collectors.toSet());
        assertEquals(billingIds, rib.getBillingIds());
    }

    @Test
    void testRibEquals() {
        String username = "John";
        String iban = "FR1420041010050500013M02606";
        String bic = "BNPAFRPPXXX";
        String cleRib = "05";
        Rib rib1 = new Rib(username, iban, bic, cleRib);
        Rib rib2 = new Rib(username, iban, bic, cleRib);
        RibId ribId= new RibId(4L);

        assertEquals(rib1, rib2);
        assertNotEquals(rib1, ribId);
    }
     Stream<Billing> billingStream(){
        Billing billing1 = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        Billing billing2 = new Billing(user2,rib2,tKcompany,period,ttc,ht,billingFile);
       return   Stream.of(billing1,billing2);
    }
    Stream<Billing> billingWithIdStream(){
        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        Billing billing1 =new Billing(new BillingId(2L),billing.getUserId(),billing.getRibId(),billing.getCompanyId()
                ,billing.getPeriod(),billing.getAmountTTC(),billing.getAmountHT(),billing.getFile()
                , billing.getPaymentStatus(),billing.getValidationStatus(),billing.getCreatedDate(),billing.getUpdatedDate());

        Billing billing2 = new Billing(new BillingId(9L),user.getId(),rib1.getId(),billing.getCompanyId()
                ,billing.getPeriod(),billing.getAmountTTC(),billing.getAmountHT(),billing.getFile()
                , billing.getPaymentStatus(),billing.getValidationStatus(),billing.getCreatedDate(),billing.getUpdatedDate());

        return   Stream.of(billing1,billing2);
    }
    Set<Billing> billingSet(int n){
        Set<Billing> billings=new HashSet<>();
        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
        LongStream.range(1,n). forEach(l-> billings.add(new Billing(new BillingId(l),billing.getUserId(),billing.getRibId(),billing.getCompanyId()
                ,billing.getPeriod(),billing.getAmountTTC(),billing.getAmountHT(),billing.getFile()
                , billing.getPaymentStatus(),billing.getValidationStatus(),billing.getCreatedDate(),billing.getUpdatedDate())));

        Billing billingWithRib2=new Billing(new BillingId(65L),billing.getUserId(),rib2.getId(),billing.getCompanyId()
                ,billing.getPeriod(),billing.getAmountTTC(),billing.getAmountHT(),billing.getFile()
                , billing.getPaymentStatus(),billing.getValidationStatus(),billing.getCreatedDate(),billing.getUpdatedDate());
              billings.add(billingWithRib2);
        return   billings;
    }
    Billing billingWithStatus(Long id, PaymentStatus paymentStatus){

        Billing billing = new Billing(user,rib1,tKcompany,period,ttc,ht,billingFile);
       return new Billing(new BillingId(id),billing.getUserId(),billing.getRibId(),billing.getCompanyId()
                ,billing.getPeriod(),billing.getAmountTTC(),billing.getAmountHT(),billing.getFile()
                , paymentStatus,billing.getValidationStatus(),billing.getCreatedDate(),billing.getUpdatedDate());

    }
    Set<Billing> billingSet(int n,Rib rib){
        Set<Billing> billings=new HashSet<>();
        Billing billing = new Billing(user,rib,tKcompany,period,ttc,ht,billingFile);
        LongStream.range(1,n). forEach(l-> billings.add(new Billing(new BillingId(l),billing.getUserId(),billing.getRibId(),billing.getCompanyId()
                ,billing.getPeriod(),billing.getAmountTTC(),billing.getAmountHT(),billing.getFile()
                , billing.getPaymentStatus(),billing.getValidationStatus(),billing.getCreatedDate(),billing.getUpdatedDate())));
        return   billings;
    }
}
