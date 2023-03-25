package freelance.service.utils;

import freelance.domain.models.entity.Billing;
import freelance.domain.models.entity.Freelance;
import freelance.domain.models.entity.Rib;
import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QueryToDomainMapper {
    QueryToDomainMapper INSTANCE = Mappers.getMapper( QueryToDomainMapper.class );

    //Rib map(freelance.service.query.model.Rib rib);
    /*
    Billing map(freelance.service.query.model.Billing billing);
    User map(freelance.service.query.model.User user);
    List<Billing> map(List<freelance.service.query.model.Billing> sourceList);

    @Mappings({
            @Mapping(source = "id", target = "id.value"),
            @Mapping(source = "iban", target = "iban.value"),
            @Mapping(source = "bic", target = "bic.value"),
            @Mapping(source = "cleRib", target = "cleRib.value"),
    })
    Rib toDomain(freelance.service.query.model.Rib rib);

    static User map(freelance.service.query.model.User queryUser){
        return new User(new UserId(queryUser.getId()), queryUser.getFirstName(), queryUser.getLastName(), new Email(queryUser.getEmail()),null,
                queryUser.getProfiles().stream().map(Profile::valueOf).collect(Collectors.toSet()), queryUser.isActive());
    }
    */
    static Rib map(freelance.service.query.model.Rib queryRib){
        return new Rib(new RibId(queryRib.getId()), queryRib.getUsername(), new Iban(queryRib.getIban()),new Bic(queryRib.getBic()),new CleRib(queryRib.getCleRib()));
    }
    static Billing map(freelance.service.query.model.Billing queryBilling){
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
       /* freelance.service.query.model.Rib queryRib = new freelance.service.query.model.Rib();
        queryRib.setId(1L);
        queryRib.setUsername("john.doe");
        queryRib.setIban("FR1420041010050500013M02606");
        queryRib.setBic("CEPAPAR1XXX");
        queryRib.setCleRib("98");*/