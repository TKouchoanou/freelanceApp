package freelance.domain.models.entity;

import freelance.domain.models.objetValue.EmployeeRole;
import freelance.domain.models.objetValue.UserId;

import java.time.LocalDateTime;

public class Employee extends Auditable{
    UserId userId;
    EmployeeRole employeeRole;

}
