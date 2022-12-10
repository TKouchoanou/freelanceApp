package freelance.domain.models.objetValue;

public enum ValidationStatus {
    IS_PROCESSING(1),  NOT_VALIDATE(1),VALIDATE(2);
    private final Integer value;

    ValidationStatus(Integer value){
        this.value=value;
    }

    public Integer getValue() {
        return value;
    }
}
