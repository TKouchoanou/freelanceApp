package freelance.domain.models.entity;

import freelance.domain.models.objetValue.Bic;
import freelance.domain.models.objetValue.CleRib;
import freelance.domain.models.objetValue.Iban;
import freelance.domain.models.objetValue.RibId;

public class Rib extends Auditable{

    public Rib(String username,String iban,String bic,String cleRib){
     this.username=username;   this.bic=new Bic(bic); this.iban=new Iban(iban); this.cleRib=new CleRib(cleRib);
    }
    public Rib( RibId id,String username, String iban,String bic,String cleRib){
            this(username, iban, bic, cleRib);
             this.id=id;
    }
    public Rib(RibId id,Iban iban,Bic bic,CleRib cleRib){
            this.id=id;   this.bic=bic; this.iban=iban; this.cleRib=cleRib;
        }
        String username;
        RibId id;
        Iban iban ;
        Bic bic;
        CleRib cleRib;
}
