package freelance.domain.models.objetValue;

public enum Profile {
    FREELANCE(0),SALARY(1);
    private final Integer value;
    Profile(Integer value){
        this.value=value;

    }
}
