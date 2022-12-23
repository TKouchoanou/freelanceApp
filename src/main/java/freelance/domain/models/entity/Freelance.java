package freelance.domain.models.entity;

import freelance.domain.annotation.SideEffectOnParameters;
import freelance.domain.exception.DomainException;
import freelance.domain.models.objetValue.*;
import jakarta.annotation.Nonnull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Freelance extends Auditable implements Rib.RibUser {
    FreelanceId id;
    UserId userId;
    RibId ribId;
    CompanyId companyId;
    Set<BillingId> billingIds=new HashSet<>();

    public Freelance(@Nonnull FreelanceId id, UserId userId, RibId ribId, CompanyId companyId, LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(createdDate, updatedDate);
        this.userId = userId;
        this.ribId = ribId;
        this.companyId=companyId;
        this.id=id;
    }
    @SideEffectOnParameters(ofType={User.class})
    public Freelance(User user, Rib rib,LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(createdDate, updatedDate);
        this.userId = user.getId();
        this.ribId = rib.getId();
        user.addFreeLanceProfile(this);
    }
    @SideEffectOnParameters(ofType={User.class})
    public Freelance(User user, Rib rib) {
        this.userId = user.getId();
        this.ribId = rib.getId();

        user.addFreeLanceProfile(this);
    }
    @SideEffectOnParameters(ofType={User.class,Company.class})
    public Freelance(User user, Company company) {
        this.setCompanyRib(company);
        company.addFreelance(this);
        this.userId = user.getId();
        user.addFreeLanceProfile(this);
    }
    private void setCompany(Company company,Boolean useCompanyRib){
        if(this.companyId!=null){
            throw new DomainException("this freelance has already company, may be instead of set company, change company");
        }
        if(useCompanyRib){
           this.setCompanyRib(company);
        }
        this.companyId=company.getId();
    }
    public void changeCompany(Company old,Company current){
        if(this.hasCompanyRib(old)){
            this.setCompanyRib(current);
        }
        this.companyId=current.getId();
    }
    protected void changeRib(Rib rib){
        if(rib.getId()==null){
            throw  new DomainException("can not change rib because you provide rib with null id");
        }
        this.ribId=rib.getId();
    }
    public boolean isThisUser(User user){
        return user!=null && userId==user.getId();
    }
    public boolean isOwnerOf(Billing billing){
       return billingIds.stream().anyMatch(b->b.equals(billing.id));
    }
    public boolean isCurrentRib(Rib rib){
       return rib!=null && this.ribId==rib.getId();
    }
    private void setCompanyRib(Company company){
        if(company.hasNotRib()){
            throw new DomainException("cannot create freelance with company without rib");
        }
        this.ribId=company.getRibId();
    }
   public boolean hasNotRib(){
        return ribId==null;
   }
   public boolean hasCompanyRib(Company company){
      return   this.ribId==company.getRibId();
   }
    public FreelanceId getId() {
        return id;
    }

}
