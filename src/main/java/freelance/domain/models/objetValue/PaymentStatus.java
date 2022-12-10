package freelance.domain.models.objetValue;

public enum PaymentStatus {
    PAID(2),WAITING_VALIDATION(0),IS_PROCESSING(1);

    private final Integer value;

    PaymentStatus(Integer value){
        this.value=value;
    }

    public Integer getValue() {
        return value;
    }
}
