package freelance.domain.models.entity;

import java.time.LocalDateTime;

public abstract class Auditable {
    Auditable(LocalDateTime createdDate,LocalDateTime updatedDate){
        if(createdDate.isAfter(updatedDate))
            throw new IllegalArgumentException("created date can't be before updatedDate");
        this.createdDate=createdDate; this.updatedDate=updatedDate;
    }
    Auditable(){
        this.createdDate=LocalDateTime.now();
        this.updatedDate=LocalDateTime.now();
    }

    LocalDateTime createdDate;
    LocalDateTime updatedDate;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

}
