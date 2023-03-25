package service.query.model;

import freelance.service.query.model.User;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    void testBuilder() {
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .profiles(Set.of("freelance"))
                .isActive(true)
                .build();

        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@gmail.com", user.getEmail());
        assertTrue(user.getProfiles().contains("freelance"));
        assertTrue(user.isActive());
    }
}
