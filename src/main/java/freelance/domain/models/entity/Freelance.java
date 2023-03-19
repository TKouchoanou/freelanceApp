package freelance.domain.models.entity;

import freelance.domain.annotation.SideEffectOnParameters;
import freelance.domain.exception.DomainException;
import freelance.domain.models.objetValue.*;
import freelance.domain.security.Auth;
import jakarta.annotation.Nonnull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Freelance extends Auditable {
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
    public Freelance(User user, Rib rib, Company company) {
        this.userId = user.getId();
        this.ribId = rib.getId();
        this.companyId=company.getId();
        user.addFreeLanceProfile(this);
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
    protected void changeCompany(Company old,Company current){
        if(this.hasCompanyRib(old)){
            this.setCompanyRib(current);
        }
        this.companyId=current.getId();
    }
    public void changeCompany(Company old, Company current, Auth auth){
        if(auth.hasNoneOfRoles(EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE)){
            throw  new DomainException("cannot perfoerm this action");
        }
        changeCompany(old, current);
    }
    protected void changeRib(Rib rib){
        if(rib.getId()==null){
            throw  new DomainException("can not change rib because you provide rib with null id");
        }
        if(rib.getId().equals(this.ribId)){
            return;
        }
        this.ribId=rib.getId();
    }
    public void changeRib(Rib rib,Auth auth){
        if(auth.hasNoneOfRoles(EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE)){
            throw  new DomainException("cannot perfoerm this action");
        }
        changeRib(rib);
    }
    public boolean isThisUser(User user){
        return user!=null && userId==user.getId();
    }
    public boolean isOwnerOf(Billing billing){
       return billingIds.stream().anyMatch(b->b.equals(billing.id));
    }

    private void setCompanyRib(Company company){
        if(company.hasNotRib()){
            throw new DomainException("cannot create freelance with company without rib");
        }
        this.ribId=company.getRibId();
    }
   public boolean hasCompanyRib(Company company){
      return   this.ribId==company.getRibId();
   }
    public FreelanceId getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public RibId getRibId() {
        return ribId;
    }

    public CompanyId getCompanyId() {
        return companyId;
    }
}
