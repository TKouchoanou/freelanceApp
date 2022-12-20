package freelance.domain.models.entity;

import freelance.domain.exception.DomainException;
import freelance.domain.models.objetValue.*;
import freelance.domain.security.Auth;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;

import static freelance.domain.models.objetValue.PaymentStatus.PAID;
import static freelance.domain.models.objetValue.ValidationStatus.*;


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

    public Billing(LocalDateTime createdDate, LocalDateTime updatedDate, BillingId id, UserId freelanceId, RibId ribId, CompanyId companyId, Period period, Money amountTTC, Money amountHT, BillingFile file, PaymentStatus paymentStatus, ValidationStatus validationStatus) {
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

    public Billing(UserId freelanceId, RibId ribId, CompanyId companyId, Period period, Money amountTTC, Money amountHT, BillingFile file) {
        this.userId = freelanceId;
        this.ribId = ribId;
        this.companyId = companyId;
        this.period = period;
        this.amountTTC = amountTTC;
        this.amountHT = amountHT;
        this.file = file;
    }
    private void  changeFile(BillingFile file,Auth auth){
        assertIsAdminOrHumanRessourceOrOwner(auth);
        if(validationStatus.isAfterOrEqual(VALIDATE) && auth.hasNoneOfRoles(EmployeeRole.ADMIN,EmployeeRole.FINANCE)){
            throw new DomainException(" you don't have enough right to perform this action");
        }
        changeFile(file);
    }
    private void  changeFile(BillingFile file){
       assertIsNotPaid();
       this.file=file;
    }
    private void  changeRib(Rib rib,Rib.RibUser freelance , Auth auth){
      assertIsAdminOrHumanRessourceOrOwner(auth);
      changeRib(rib,freelance);
    }
    private void  changeRib(Rib rib, Rib.RibUser ribUser){
        if(this.validationStatus.isAfterOrEqual(VALIDATE)){
            throw new DomainException(" impossible to change validate billing rib, invalidate billing before");
        }
        if(!(ribUser !=null && ribUser.isOwnerOf(this) && ribUser.isCurrentRib(rib))){
            throw new DomainException("unknown");
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
    public void  assertIsNotPaid(){
        if(this.paymentStatus.isAfterOrEqual(PAID)){
            throw new DomainException("could perform the modification, because the billing is paid");
        }
    }

    public BillingId getId() {
        return id;
    }
}
