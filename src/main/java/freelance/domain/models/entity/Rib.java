package freelance.domain.models.entity;

import freelance.domain.exception.DomainException;
import freelance.domain.models.objetValue.*;
import freelance.domain.security.Auth;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Rib extends Auditable{
    public interface RibUser {
        boolean isOwnerOf(Billing billing);
        boolean isCurrentRib(Rib rib);
    }
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
      Set<BillingId> billingIds=new HashSet<>(); //en ecriture on a besoin de charger ça en eager contrairement à la lecture

   public void update(String username, String iban, String bic, String cleRib){
       this.username=username;   this.bic=new Bic(bic); this.iban=new Iban(iban); this.cleRib=new CleRib(cleRib);
   }
    public Rib update(String username, String bic, String cleRib, Auth auth){
        if(auth.hasNoneOfRoles(EmployeeRole.HUMAN_RESOURCE,EmployeeRole.ADMIN)){
            //au pire si le freelance peut modifier son rib il faut qu'il soumette une demande qui sera valider par un salarié
            throw new DomainException(" current user grant is not enough to update Rib");
        }
        this.username=username;   this.bic=new Bic(bic); this.cleRib=new CleRib(cleRib);
        return this;
    }
    public Rib update(String iban, Set<Billing> billings, Auth auth){
        if(auth.hasNoneOfRoles(EmployeeRole.ADMIN)){
            throw new DomainException(" current user grant is not enough to update Rib");
        }

        Map<BillingId,Billing> billingMap= billings.stream().collect(Collectors.toMap(Billing::getId, Function.identity()));
        for (BillingId billingId: this.billingIds) {
            if(!billingMap.containsKey(billingId)){
                throw new DomainException("billing with id "
                        +billingId+" with his associate to this Rib is not provide in the parameter list of billing");
            }
            Billing currentBilling= billingMap.get(billingId);

            if(currentBilling.getPaymentStatus().isAfterOrEqual(PaymentStatus.PAID)){
                throw new DomainException(" Rib with id "+this.id+" has been use for to paid Billing with id "
                        +billingId+" it's iban cannot be change. May be create new Rib");
            }
        }
        this.iban=new Iban(iban);
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
