package freelance.domain.models.entity;

import freelance.domain.exception.DomainException;
import freelance.domain.models.objetValue.*;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;
import java.util.Set;


public class Billing  extends Auditable {
    BillingId id;
    UserId freelanceId;
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
        this.freelanceId = freelanceId;
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
        this.freelanceId = freelanceId;
        this.ribId = ribId;
        this.companyId = companyId;
        this.period = period;
        this.amountTTC = amountTTC;
        this.amountHT = amountHT;
        this.file = file;
    }
    public void update(Rib rib,Company company,Period period){
        if(!company.getId().equals(this.companyId)){
            throw new DomainException(" the current company is not this billing company");
        }
        if(!company.hasThisRib(rib)){
            throw new DomainException(" the current rib is not this billing company rib");
        }
        if(this.validationStatus.isValidate()){
            throw new DomainException(" impossible to change validate billing rib, invalidate billing before");
        }
        this.ribId=rib.getId();
    }
    public void update(Money amountTTC,Money amountHT){
        if(this.validationStatus.isValidate()){
            throw new DomainException(" impossible to change validate billing amount, invalidate billing before");
        }
        this.amountHT=amountHT;
        this.amountTTC=amountTTC;
    }

    public void updatePayementStatus(PaymentStatus paymentStatus,User currentUser,Employee employee){
        if(!employee.isThisUser(currentUser)){
            throw new DomainException(" the current user is not the current employee");
        }
        Set<EmployeeRole> authorisedRoles=Set.of(EmployeeRole.FINANCE,EmployeeRole.ADMIN);
        boolean hasRight= employee.getEmployeeRoles().stream().anyMatch(authorisedRoles::contains);
        if(!hasRight){
            throw new DomainException(" the current user grant is not enough to perform this action");
        }
        if(paymentStatus.isPaid() && this.validationStatus.isNotValidate()){
            throw new DomainException(" can't change payement to paid for not validate billing");
        }
        this.paymentStatus=paymentStatus;
    }
    public void updateValidateStatus(ValidationStatus validationStatus,User currentUser,Employee employee){
        if(!employee.isThisUser(currentUser)){
            throw new DomainException(" the current user is not the current employee");
        }

        Set<EmployeeRole> authorisedRoles=Set.of(EmployeeRole.HUMAN_RESOURCE,EmployeeRole.ADMIN);
        boolean hasRight= employee.getEmployeeRoles().stream().anyMatch(authorisedRoles::contains);
        if(!hasRight){
            throw new DomainException(" the current user grant is not enough to perform this action");
        }

        if (this.paymentStatus.isPaid() && ValidationStatus.VALIDATE.isAfter(validationStatus)){
            String explain="only status after validate can be accepted for already paid billing";
            throw new DomainException(" impossible to perform this action status "+validationStatus+"  after paiement "+explain);
        }

        this.validationStatus=validationStatus;
    }
}
