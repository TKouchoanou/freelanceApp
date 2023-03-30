package freelance.interfaces.ui.billing.dto;

import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
@SuppressWarnings("unused")
public class BillingFreelanceSummaryDto {
    String firstName;
    String lastName;
    String email;
    String companyName;

    public String fullName() {
        return lastName+" "+firstName;
    }
}
