package service.query.model;

import freelance.service.query.model.FreelanceSummary;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FreelanceSummaryTest {
    @Test
    void testBuilder() {
        FreelanceSummary freelanceSummary = FreelanceSummary.builder()
                .id(1L)
                .userId(100L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .companyName("ACME Inc.")
                .ribId(200L)
                .profiles(Set.of("employee"))
                .isActive(true)
                .build();

        assertEquals(1L, freelanceSummary.getId());
        assertEquals(100L, freelanceSummary.getUserId());
        assertEquals("John", freelanceSummary.getFirstName());
        assertEquals("Doe", freelanceSummary.getLastName());
        assertEquals("john.doe@gmail.com", freelanceSummary.getEmail());
        assertEquals("ACME Inc.", freelanceSummary.getCompanyName());
        assertEquals(200L, freelanceSummary.getRibId());
        assertTrue(freelanceSummary.getProfiles().contains("employee"));
        assertTrue(freelanceSummary.isActive());
    }

}
