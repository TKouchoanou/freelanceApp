package freelance.domain.models.entity;

import freelance.domain.annotation.SideEffectOnParameters;
import freelance.domain.exception.DomainException;
import freelance.domain.models.objetValue.*;
import freelance.domain.security.Auth;
import jakarta.annotation.Nonnull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Company extends Auditable  {
    CompanyId id;
    String name;
    RibId ribId;
    Set<BillingId> billingIds=new HashSet<>();
    Set<FreelanceId> freelanceIds=new HashSet<>();

    public Company(@Nonnull CompanyId id, String name, RibId ribId,  Set<FreelanceId> freelanceIds,Set<BillingId> billingIds,LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(createdDate, updatedDate);
        this.id = id;
        this.name = name;
        this.ribId=ribId;
        this.billingIds=billingIds;
        this.freelanceIds=freelanceIds;
    }

    public Company(Rib rib, String name) {
        this.ribId=rib.getId();
        this.name = name;
    }

    public RibId getRibId() {
        return ribId;
    }
    public boolean hasThisRib(Rib rib){
       return this.ribId==rib.getId();
    }

    protected void changeRib(Rib rib, Auth auth){
      if(auth.hasNoneOfRoles(EmployeeRole.ADMIN,EmployeeRole.HUMAN_RESOURCE)){
          throw new DomainException("can not perform this action");
      }
      this.ribId=rib.getId();
    }
    @SideEffectOnParameters(ofType={Freelance.class})
    private Set<Freelance>  changeRib(Rib rib,Set<Freelance> freelances){
        // si les freelances modifiés ne sont pas persistés, il y a incohérence
        Map<FreelanceId,Freelance> freelanceByIdMap =freelances.stream().collect(Collectors.toMap(Freelance::getId, Function.identity()));
        for (FreelanceId id: this.freelanceIds) {
            if(!freelanceByIdMap.containsKey(id)){
                throw new DomainException("Freelance with id "
                        +id+" witch is associate to company "+this.id+" is not provide in the parameter list of freelances");
            }
            Freelance current= freelanceByIdMap.get(id);
            if(current.hasCompanyRib(this)){
                current.changeRib(rib);
            }
        }
        return  freelances;
    }
    @SideEffectOnParameters(ofType={Freelance.class})
    public Set<Freelance>  changeRib(Rib rib,Set<Freelance> freelances,Auth auth){
      if(auth.hasNoneOfRoles(EmployeeRole.HUMAN_RESOURCE,EmployeeRole.ADMIN)){
          throw new DomainException("The auth "+auth.getEmail()+" doesn't have enough right to perform this action");
      }
        return  changeRib(rib,freelances);
    }

    protected void addBilling(Billing billing){
        if(billing.getId()==null){
            throw new DomainException("add not persisted billing to company");
        }
        this.billingIds.add(billing.getId());
    }

    protected void addFreelance(Freelance freelance){
        if(freelance.getId()==null){
            throw new DomainException("add not persisted freelance to company");
        }
        this.freelanceIds.add(freelance.getId());
    }
   public void addFreelance(Freelance freelance,Auth auth){
        if(auth.hasNoneOfRoles(EmployeeRole.HUMAN_RESOURCE,EmployeeRole.ADMIN)){
            throw new  DomainException("current auth not have enought right to perform this action");
        }
        addFreelance(freelance);

   }
   public boolean isOwnerOf(Billing billing){
       return this.billingIds.stream().anyMatch(b->b.equals(billing.getId()));
    }
    public boolean isCurrentRib(Rib rib){
        return rib!=null && ribId==rib.getId();
    }

    public boolean hasRib(){
        return this.ribId!=null;
    }
    public boolean hasNotRib(){
        return this.ribId==null;
    }

    public CompanyId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<BillingId> getBillingIds() {
        return billingIds;
    }

    public Set<FreelanceId> getFreelanceIds() {
        return freelanceIds;
    }
}
