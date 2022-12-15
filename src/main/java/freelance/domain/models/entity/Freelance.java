package freelance.domain.models.entity;

import freelance.domain.exception.DomainException;
import freelance.domain.models.objetValue.RibId;
import freelance.domain.models.objetValue.UserId;

import java.time.LocalDateTime;

public class Freelance extends Auditable {
    UserId userId;
    RibId ribId;

    public Freelance(LocalDateTime createdDate, LocalDateTime updatedDate, UserId userId, RibId ribId) {
        super(createdDate, updatedDate);
        this.userId = userId;
        this.ribId = ribId;
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
}
