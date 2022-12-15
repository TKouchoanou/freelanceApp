package freelance.domain.models.entity;

import freelance.domain.models.objetValue.CompanyId;
import freelance.domain.models.objetValue.EmployeeId;
import freelance.domain.models.objetValue.RibId;

import java.time.LocalDateTime;

public class Company extends Auditable {
    CompanyId id;
    String name;
    RibId ribId;

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

    public void changeRib(Rib rib){

    }


    public CompanyId getId() {
        return id;
    }
}
