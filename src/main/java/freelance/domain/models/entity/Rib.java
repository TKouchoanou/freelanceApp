package freelance.domain.models.entity;

import freelance.domain.models.objetValue.Bic;
import freelance.domain.models.objetValue.CleRib;
import freelance.domain.models.objetValue.Iban;
import freelance.domain.models.objetValue.RibId;

import java.time.LocalDateTime;

public class Rib extends Auditable{

    public Rib(String username,String iban,String bic,String cleRib){
     this.username=username;   this.bic=new Bic(bic); this.iban=new Iban(iban); this.cleRib=new CleRib(cleRib);
    }
    public Rib(RibId id,String username, String iban,String bic,String cleRib,LocalDateTime createdDate, LocalDateTime updatedDate){
            this(id,username, new Iban(iban), new Bic(bic), new CleRib(cleRib),createdDate,updatedDate);
    }
    public Rib(RibId id, String username, Iban iban,Bic bic,CleRib cleRib,LocalDateTime createdDate, LocalDateTime updatedDate){
            super(createdDate,updatedDate);
            this.id=id; this.username=username;  this.bic=bic; this.iban=iban; this.cleRib=cleRib;
        }
    public Rib(RibId id, String username, Iban iban,Bic bic,CleRib cleRib){
        this.id=id; this.username=username;  this.bic=bic; this.iban=iban; this.cleRib=cleRib;
    }
        String username;
        RibId id;
        Iban iban ;
        Bic bic;
        CleRib cleRib;

   public Rib update(String username,String iban,String bic,String cleRib){
       this.username=username;   this.bic=new Bic(bic); this.iban=new Iban(iban); this.cleRib=new CleRib(cleRib);
       return this;
   }

    public RibId getId() {
        return id;
    }

    public Bic getBic() {
        return bic;
    }

    public Iban getIban() {
        return iban;
    }

    public CleRib getCleRib() {
        return cleRib;
    }

    public String getUsername() {
        return username;
    }
}
