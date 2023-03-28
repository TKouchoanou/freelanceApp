package service.query.model;

import freelance.application.query.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {
    @Test
    void testSetter() {
        Employee employee = Employee.builder()
                .id(1L)
                .userid(100L)
                .roles(Set.of("admin"))
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .profiles(Set.of("freelance"))
                .isActive(true)
                .build();

        employee.setId(2L);
        employee.setUserid(200L);
        employee.setRoles(Set.of("manager"));
        employee.setFirstName("Jane");
        employee.setLastName("Smith");
        employee.setEmail("jane.smith@gmail.com");
        employee.setProfiles(Set.of("employee"));
        employee.setActive(false);

        assertEquals(2L, employee.getId());
        assertEquals(200L, employee.getUserid());
        assertEquals(Set.of("manager"), employee.getRoles());
        assertEquals("Jane", employee.getFirstName());
        assertEquals("Smith", employee.getLastName());
        assertEquals("jane.smith@gmail.com", employee.getEmail());
        assertTrue(employee.getProfiles().contains("employee"));
        assertFalse(employee.isActive());
    }
}
