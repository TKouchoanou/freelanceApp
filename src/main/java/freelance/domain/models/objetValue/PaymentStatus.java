package freelance.domain.models.objetValue;

public enum PaymentStatus{
    WAITING_VALIDATION(0),IS_PROCESSING(1),PAID(2);

    private final Integer code;

    PaymentStatus(Integer value){
        this.code =value;
    }

    public Integer getCode() {
        return code;
    }
    public boolean isPaid(){
        return this.equals(PAID);
    }
    public int customCompareTo(PaymentStatus paymentStatus){
       return this.code-paymentStatus.code;
    }
    public boolean isAfterOrEqual(PaymentStatus paymentStatus){
        return customCompareTo(paymentStatus)>=0;
    }
    public boolean isBefore(PaymentStatus paymentStatus){
        return customCompareTo(paymentStatus)<0;
    }

}
