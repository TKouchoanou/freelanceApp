package freelance.domain.models.entity;

import freelance.domain.exception.DomainException;
import freelance.domain.models.objetValue.*;
import freelance.domain.security.Auth;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Company extends Auditable implements Rib.RibUser {
    CompanyId id;
    String name;
    RibId ribId;
    Set<BillingId> billingIds=new HashSet<>();

    public Company(CompanyId id, String name,RibId ribId,LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(createdDate, updatedDate);
        this.id = id;
        this.name = name;
        this.ribId=ribId;
    }

    public Company(CompanyId id, String name) {
        this.id = id;
        this.name = name;
    }

    public RibId getRibId() {
        return ribId;
    }
    public boolean hasThisRib(Rib rib){
       return this.ribId==rib.getId();
    }

    public void changeRib(Rib rib, Auth auth){
      if(auth.hasNoneOfRoles(EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE)){
          throw new DomainException("can not perform this action");
      }
      this.ribId=rib.getId();
    }
   public boolean isOwnerOf(Billing billing){
       return this.billingIds.stream().anyMatch(b->b.equals(billing.getId()));
    }
    public boolean isCurrentRib(Rib rib){
        return rib!=null && ribId==rib.getId();
    }

    public CompanyId getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
