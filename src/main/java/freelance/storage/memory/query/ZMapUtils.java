package freelance.storage.memory.query;

import freelance.domain.models.entity.Company;
import freelance.domain.models.entity.Rib;
import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.EmployeeRole;
import freelance.domain.models.objetValue.Profile;
import freelance.service.query.model.*;

import java.util.Set;
import java.util.stream.Collectors;

public class ZMapUtils {

   public static freelance.service.query.model.User toQuery(freelance.domain.models.entity.User user){
        return    freelance.service.query.model.User.builder()
                .email(user.getEmail().value())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.isActive())
                .id(user.getId().id())
                .profiles(user.getProfiles().stream().map(Profile::name).collect(Collectors.toSet()))
                .build();
    }
    public static freelance.service.query.model.Rib toQuery(freelance.domain.models.entity.Rib rib){
        return  freelance.service.query.model.Rib.builder()
                .username(rib.getUsername())
                .cleRib(rib.getCleRib().value())
                .iban(rib.getIban().value())
                .bic(rib.getBic().value())
                .id(rib.getId().value())
                .build();
    }

    public static  freelance.service.query.model.Company toQuery(freelance.domain.models.entity.Company company){
        return  freelance.service.query.model.
                Company
                .builder()
                .ribId(company.getRibId().value())
                .id(company.getId().id())
                .name(company.getName())
                .build();
    }
    public static   freelance.service.query.model.Billing toQuery(freelance.domain.models.entity.Billing billing, freelance.domain.models.entity.Company company){
        return  freelance.service.query.model.
                Billing
                .builder()
                .ended(billing.getPeriod().ended())
                .started(billing.getPeriod().started())
                .amountHT(billing.getAmountHT())
                .amountTTC(billing.getAmountTTC())
                .fileUri(billing.getId().id())
                .company(toQuery(company))
                .paymentStatus(billing.getPaymentStatus().name())
                .validationStatus(billing.getValidationStatus().name())
                .build();
    }

    public static  freelance.service.query.model.BillingSummary toQuery(freelance.domain.models.entity.Billing billing){
        return  freelance.service.query.model.
                BillingSummary
                .builder()
                .ended(billing.getPeriod().ended())
                .started(billing.getPeriod().started())
                .amountHT(billing.getAmountHT())
                .amountTTC(billing.getAmountTTC())
                .fileUri(billing.getId().id().toString())
                .validationStatus(billing.getValidationStatus().name())
                .paymentStatus(billing.getPaymentStatus().name())
                .id(billing.getId().id())
                .build();
    }

   public static Freelance toQuery(freelance.domain.models.entity.Freelance freelance, User user, Rib rib, Company company, Set<BillingSummary> billings){
        return  Freelance.builder()
                .user(ZMapUtils.toQuery(user))
                .rib(ZMapUtils.toQuery(rib))
                .company(ZMapUtils.toQuery(company))
                .id(freelance.getCompanyId().id())
                .billing(billings)
                .build();
    }
    public static FreelanceSummary toQuery(freelance.domain.models.entity.Freelance freelance, User user, Company company){
        return  FreelanceSummary.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .companyName(company.getName())
                .email(user.getEmail().value())
                .isActive(user.isActive())
                .userId(user.getId().id())
                .ribId(freelance.getRibId().value())
                .profiles(user.getProfiles().stream().map(Profile::name).collect(Collectors.toSet()))
                .build();
    }

    public static  Employee toQuery(freelance.domain.models.entity.Employee employee,User user){
       return Employee
               .builder()
               .roles(employee.getEmployeeRoles().stream().map(EmployeeRole::name).collect(Collectors.toSet()))
               .email(user.getEmail().value())
               .lastName(user.getLastName())
               .firstName(user.getFirstName())
               .isActive(user.isActive())
               .userid(user.getId().id())
               .profiles(user.getProfiles().stream().map(Profile::name).collect(Collectors.toSet()))
               .build();
    }
}
