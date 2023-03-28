package service.query.model;


import freelance.application.query.model.*;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FreelanceTest {

    @Test
    void testGetter() {
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .profiles(Set.of("freelance"))
                .isActive(true)
                .build();

        Rib rib = Rib.builder()
                .id(1L)
                .username("john.doe")
                .iban("FR1420041010050500013M02606")
                .bic("AGRIFRPPXXX")
                .cleRib("13")
                .build();

        Company company = Company.builder()
                .id(1L)
                .name("ACME Corp.")
                .ribId(1L)
                .build();

        BillingSummary billingSummary = BillingSummary.builder()
                .id(1L)
                .started(LocalDate.of(2023, 3, 1))
                .ended(LocalDate.of(2023, 3, 31))
                .amountTTC(Money.of(1000,"EUR"))
                .amountHT(Money.of(800,"EUR"))
                .fileUri("http://localhost:8080/billing/1")
                .paymentStatus("paid")
                .validationStatus("validated")
                .build();

        Freelance freelance = Freelance.builder()
                .id(1L)
                .user(user)
                .rib(rib)
                .company(company)
                .billing(Set.of(billingSummary))
                .build();

        assertEquals(1L, freelance.getId());
        assertEquals(user, freelance.getUser());
        assertEquals(rib, freelance.getRib());
        assertEquals(company, freelance.getCompany());
        assertTrue(freelance.getBilling().contains(billingSummary));
    }
}