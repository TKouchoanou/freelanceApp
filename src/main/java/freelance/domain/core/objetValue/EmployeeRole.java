package freelance.domain.core.objetValue;

public enum EmployeeRole {
    HUMAN_RESOURCE(0),FINANCE(1),ADMIN(2);
    final Integer value;
    EmployeeRole(Integer value){
        this.value=value;
    }
}
