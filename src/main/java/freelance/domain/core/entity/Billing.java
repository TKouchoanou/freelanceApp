package freelance.domain.core.entity;

import freelance.domain.common.annotation.SideEffectOnParameters;
import freelance.domain.common.exception.DomainException;
import freelance.domain.core.objetValue.*;
import freelance.domain.common.security.Auth;
import jakarta.annotation.Nullable;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;
import java.util.Optional;

import static freelance.domain.core.objetValue.PaymentStatus.PAID;
import static freelance.domain.core.objetValue.ValidationStatus.IS_PROCESSING;
import static freelance.domain.core.objetValue.ValidationStatus.VALIDATE;


public class Billing  extends Auditable {
    BillingId id;
    UserId userId;
    RibId ribId;
    CompanyId companyId;
    Period period;
    Money amountTTC;
    Money amountHT;
    BillingFile file;
    PaymentStatus paymentStatus;
    ValidationStatus validationStatus;

    public Billing(BillingId id, UserId freelanceId, RibId ribId, CompanyId companyId, Period period, Money amountTTC, Money amountHT, BillingFile file, PaymentStatus paymentStatus, ValidationStatus validationStatus,LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(createdDate, updatedDate);
        this.id = id;
        this.userId = freelanceId;
        this.ribId = ribId;
        this.companyId = companyId;
        this.period = period;
        this.amountTTC = amountTTC;
        this.amountHT = amountHT;
        this.file = file;
        this.paymentStatus = paymentStatus;
        this.validationStatus = validationStatus;
    }

    public Billing(User freelance, Rib rib, @Nullable Company company, Period period, Money amountTTC, Money amountHT, BillingFile file) {
        this.userId = freelance.getId();
        this.ribId = rib.getId();
        this.companyId = Optional.ofNullable(company).map(Company::getId).orElse(null);
        this.period = period;
        this.amountTTC = amountTTC;
        this.amountHT = amountHT;
        this.file = file;
        this.paymentStatus=PaymentStatus.WAITING_VALIDATION;
        this.validationStatus= IS_PROCESSING;
    }
    public void  changeFile(BillingFile file,Auth auth){
        assertIsAdminOrHumanRessourceOrOwner(auth);
        if(validationStatus.isAfterOrEqual(VALIDATE) && auth.hasNoneOfRoles(EmployeeRole.ADMIN,EmployeeRole.FINANCE)){
            throw new DomainException(" you don't have enough right to perform this action");
        }
        changeFile(file);
    }
    private void changeUser(User old,User newUser){
        if(old==null || old.getId()==null){
            throw new DomainException("old user cannot be null or not persisted");
        }
        if(newUser==null || newUser.getId()==null){
            throw new DomainException("new user cannot be null or not persisted");
        }
        if(old.equals(newUser)){
            return;
        }
        if(this.userId!=old.getId()){
            throw new DomainException("old user is not this billing user");
        }
        this.userId=newUser.getId();
    }
    public void changeUser(User old,User newUser,Auth auth){
        if(auth.hasNoneOfRoles(EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE)){
            throw new DomainException("you don't have enough right to perform this action");
        }
        changeUser(old, newUser);

    }
    private void  changeFile(BillingFile file){
       assertIsNotPaid();
       this.file=file;
    }
    public void  changeRib(Rib rib , Auth auth){
      assertIsAdminOrHumanRessourceOrOwner(auth);
      changeRib(rib);
    }

    private void setPeriod(Period period) {
        this.period = period;
    }
    public void changePeriod(Period period,Auth auth) {
        assertIsAdminOrHumanRessourceOrOwner(auth);
        this.period = period;
    }

    private void  changeRib(Rib rib){
        if(this.validationStatus.isAfterOrEqual(VALIDATE)){
            throw new DomainException(" impossible to change validate billing rib, invalidate billing before");
        }
        this.ribId=rib.getId();
    }

    public void  updateAmount(Money amountTTC,Money amountHT,Auth auth){
        this.assertIsAdminOrHumanRessourceOrOwner(auth);
        this.setMoney(amountTTC,amountHT);
    }
    private void setMoney(Money ttc, Money ht){
        if(this.validationStatus.isAfterOrEqual(VALIDATE)){
            throw new DomainException(" impossible to change validate billing amount, invalidate billing before");
        }
        if(ttc.isLessThan(ht)){
            throw new DomainException(" amount ttc cannot be less than amount HT");
        }

        this.amountHT= ht;
        this.amountTTC= ttc;
    }
    @SideEffectOnParameters(ofType = Company.class)
    public void changeCompany(Company company,Auth auth) {
        assertIsAdminOrHumanRessourceOrOwner(auth);
        this.companyId = company.getId();
        company.addBilling(this);
    }

    public void updatePayementStatus(PaymentStatus paymentStatus, Auth auth){
       if(auth.hasNoneOfRoles(EmployeeRole.FINANCE,EmployeeRole.ADMIN)){
            throw new DomainException(" the current auth grant is not enough to perform this action");
        }
     this.setPayementStatus(paymentStatus);
    }
    public void setPayementStatus(PaymentStatus status){
        if(status.isAfterOrEqual(PAID) && this.validationStatus.isBefore(VALIDATE)){
            throw new DomainException(" can't change payement to "+status+" for not validate billing");
        }
        this.paymentStatus=status;
    }
    public void updateValidateStatus(ValidationStatus validationStatus,Auth auth){
       if(auth.hasNoneOfRoles(EmployeeRole.HUMAN_RESOURCE,EmployeeRole.ADMIN)){
            throw new DomainException(" the current auth grant is not enough to perform this action");
        }
       this.setValidateStatus(validationStatus);
    }
    private void setValidateStatus(ValidationStatus status){
        if (this.paymentStatus.isAfterOrEqual(PAID) && status.isBefore(VALIDATE)){
            throw new DomainException(" impossible to perform this action, status cannot be change to "+status+"  after paiement ");
        }
        this.validationStatus=status;
    }

    public UserId getUserId() {
        return userId;
    }
    private void assertIsAdminOrHumanRessourceOrOwner(Auth auth){
        if(auth.hasNoneOfRoles(EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE) || auth.isOwner(this)){
            throw new DomainException(" the current user grant is not enough to perform rib modification");
        }
    }
    private void  assertIsNotPaid(){
        if(this.paymentStatus.isAfterOrEqual(PAID)){
            throw new DomainException("could perform the modification, because the billing is paid");
        }
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public BillingId getId() {
        return id;
    }

    public CompanyId getCompanyId() {
        return companyId;
    }

    public RibId getRibId() {
        return ribId;
    }

    public Money getAmountHT() {
        return amountHT;
    }

    public BillingFile getFile() {
        return file;
    }

    public ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public Money getAmountTTC() {
        return amountTTC;
    }

    public Period getPeriod() {
        return period;
    }
}
