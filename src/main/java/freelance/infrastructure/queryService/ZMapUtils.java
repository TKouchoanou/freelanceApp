package freelance.infrastructure.queryService;

import freelance.domain.core.entity.Company;
import freelance.domain.core.entity.Rib;
import freelance.domain.core.entity.User;
import freelance.domain.core.objetValue.EmployeeRole;
import freelance.domain.core.objetValue.Profile;
import freelance.application.query.model.*;

import java.util.Set;
import java.util.stream.Collectors;

public class ZMapUtils {

   public static freelance.application.query.model.User toQuery(freelance.domain.core.entity.User user){
        return    freelance.application.query.model.User.builder()
                .email(user.getEmail().value())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.isActive())
                .id(user.getId().id())
                .profiles(user.getProfiles().stream().map(Profile::name).collect(Collectors.toSet()))
                .build();
    }
    public static freelance.application.query.model.Rib toQuery(freelance.domain.core.entity.Rib rib){
        return  freelance.application.query.model.Rib.builder()
                .username(rib.getUsername())
                .cleRib(rib.getCleRib().value())
                .iban(rib.getIban().value())
                .bic(rib.getBic().value())
                .id(rib.getId().value())
                .build();
    }

    public static  freelance.application.query.model.Company toQuery(freelance.domain.core.entity.Company company){
        return  freelance.application.query.model.
                Company
                .builder()
                .ribId(company.getRibId().value())
                .id(company.getId().id())
                .name(company.getName())
                .build();
    }
    public static   freelance.application.query.model.Billing toQuery(freelance.domain.core.entity.Billing billing, freelance.domain.core.entity.Company company){
        return  freelance.application.query.model.
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

    public static  freelance.application.query.model.BillingSummary toQuery(freelance.domain.core.entity.Billing billing){
        return  freelance.application.query.model.
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

   public static Freelance toQuery(freelance.domain.core.entity.Freelance freelance, User user, Rib rib, Company company, Set<BillingSummary> billings){
        return  Freelance.builder()
                .user(ZMapUtils.toQuery(user))
                .rib(ZMapUtils.toQuery(rib))
                .company(ZMapUtils.toQuery(company))
                .id(freelance.getCompanyId().id())
                .billing(billings)
                .build();
    }
    public static FreelanceSummary toQuery(freelance.domain.core.entity.Freelance freelance, User user, Company company){
        return  FreelanceSummary.builder()
                .id(user.getId().id())
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

    public static  Employee toQuery(freelance.domain.core.entity.Employee employee, User user){
       return Employee
               .builder()
               .roles(employee.getEmployeeRoles().stream().map(EmployeeRole::name).collect(Collectors.toSet()))
               .email(user.getEmail().value())
               .lastName(user.getLastName())
               .firstName(user.getFirstName())
               .isActive(user.isActive())
               .userid(user.getId().id())
               .id(employee.getId().value())
               .profiles(user.getProfiles().stream().map(Profile::name).collect(Collectors.toSet()))
               .build();
    }
}
