package freelance.domain.models.entity;

import freelance.domain.exception.DomainException;
import freelance.domain.models.objetValue.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Freelance extends Auditable implements Rib.RibUser {
    FreelanceId id;
    UserId userId;
    RibId ribId;
    CompanyId companyId;
    Set<BillingId> billingIds=new HashSet<>();

    public Freelance(FreelanceId id, UserId userId, RibId ribId,LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(createdDate, updatedDate);
        this.userId = userId;
        this.ribId = ribId;
        this.id=id;
    }


    public Freelance(UserId userId, RibId ribId) {
        this.userId = userId;
        this.ribId = ribId;
    }
    public void changeRib(Rib rib){
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
}
