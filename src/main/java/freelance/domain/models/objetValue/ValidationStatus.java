package freelance.domain.models.objetValue;

public enum ValidationStatus {
    IS_PROCESSING(1),  NOT_VALIDATE(1),VALIDATE(2);
    private final Integer code;

    ValidationStatus(Integer value){
        this.code =value;
    }

    public Integer getCode() {
        return code;
    }
    public boolean isValidate(){
        return this.equals(VALIDATE);
    }
    public boolean isNotValidate(){
        return !this.equals(VALIDATE);
    }
    public boolean isAfter(ValidationStatus validationStatus){
        return this.customCompareTo(validationStatus) >0;
    }
    public int customCompareTo(ValidationStatus validationStatus){
        return this.code-validationStatus.code;
    }
}
