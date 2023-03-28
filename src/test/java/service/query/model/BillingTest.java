package service.query.model;

import freelance.service.query.model.Billing;
import freelance.service.query.model.BillingSummary;
import freelance.service.query.model.Company;
import freelance.service.query.model.FreelanceSummary;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillingTest {
    @Test
    void testBuilder() {
        FreelanceSummary freelance = FreelanceSummary.builder()
                .id(1L)
                .userId(100L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .companyName("ACME Inc.")
                .ribId(200L)
                .profiles(Set.of("freelance", "employee"))
                .isActive(true)
                .build();
        Company company = Company
                .builder()
                .ribId(1L)
                .name("tk company")
                .id(1L)
                .build();
        Billing billing = Billing.builder()
                .id(1L)
                .started(LocalDate.of(2022, 1, 1))
                .ended(LocalDate.of(2022, 1, 31))
                .amountTTC(Money.of(1000,"EUR"))
                .amountHT(Money.of(800,"EUR"))
                .fileUri(4L)
                .paymentStatus("Paid")
                .validationStatus("Validated")
                .freelance(freelance)
                .company(company)
                .build();

        assertEquals(1L, billing.getId());
        assertEquals(LocalDate.of(2022, 1, 1), billing.getStarted());
        assertEquals(LocalDate.of(2022, 1, 31), billing.getEnded());
        assertEquals(Money.of(1000,"EUR"), billing.getAmountTTC());
        assertEquals(Money.of(800,"EUR"), billing.getAmountHT());
        assertEquals(4L, billing.getFileUri());
        assertEquals("Paid", billing.getPaymentStatus());
        assertEquals("Validated", billing.getValidationStatus());
        assertEquals(freelance, billing.getFreelance());
        assertEquals(company, billing.getCompany());
    }

    @Test
    public void testBuilderForSummary() {
        Long id = 1L;
        LocalDate started = LocalDate.of(2022, 1, 1);
        LocalDate ended = LocalDate.of(2022, 1, 31);
        Money amountTTC = Money.of(1000,"EUR");
        Money amountHT = Money.of(700,"EUR");
        String fileUri = "/path/to/file";
        String paymentStatus = "PAID";
        String validationStatus = "VALIDATED";

        BillingSummary billingSummary = BillingSummary.builder()
                .id(id)
                .started(started)
                .ended(ended)
                .amountTTC(amountTTC)
                .amountHT(amountHT)
                .fileUri(fileUri)
                .paymentStatus(paymentStatus)
                .validationStatus(validationStatus)
                .build();

        assertEquals(id, billingSummary.getId());
        assertEquals(started, billingSummary.getStarted());
        assertEquals(ended, billingSummary.getEnded());
        assertEquals(amountTTC, billingSummary.getAmountTTC());
        assertEquals(amountHT, billingSummary.getAmountHT());
        assertEquals(fileUri, billingSummary.getFileUri());
        assertEquals(paymentStatus, billingSummary.getPaymentStatus());
        assertEquals(validationStatus, billingSummary.getValidationStatus());
    }

}
