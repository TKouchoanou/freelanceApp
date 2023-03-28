package freelance.application.utils;

import freelance.domain.core.entity.Billing;
import freelance.domain.core.entity.Rib;
import freelance.domain.core.objetValue.*;


public interface QueryToDomainMapper {
    static Rib map(freelance.application.query.model.Rib queryRib){
        return new Rib(new RibId(queryRib.getId()), queryRib.getUsername(), new Iban(queryRib.getIban()),new Bic(queryRib.getBic()),new CleRib(queryRib.getCleRib()));
    }
    static Billing map(freelance.application.query.model.Billing queryBilling){
        BillingId id= new BillingId(queryBilling.getId());
        //la vérification est faite avec l'userId
        UserId userId= new UserId(queryBilling.getFreelance().getUserId());
        RibId ribId= new RibId(queryBilling.getId());
        CompanyId companyId= new CompanyId(queryBilling.getCompany().getId());
        return new Billing(id, userId, ribId, companyId,
                null,queryBilling.getAmountTTC(),queryBilling.getAmountHT(),
                null,PaymentStatus.valueOf(queryBilling.getPaymentStatus()),ValidationStatus.valueOf(queryBilling.getValidationStatus()),
                null,null);
    }

}